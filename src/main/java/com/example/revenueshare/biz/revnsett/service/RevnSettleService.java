package com.example.revenueshare.biz.revnsett.service;

import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.domain.repository.ChannelRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRsMastIds;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRsMastRepository;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCmpDTO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCrtDTO;
import com.example.revenueshare.biz.mng.chrevn.service.ChannelRevnMastCmpMngService;
import com.example.revenueshare.biz.mng.chrevn.service.ChannelRevnMastCrtMngService;
import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCmpnyRepository;
import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCreatorRepository;
import com.example.revenueshare.biz.revnsett.model.RevnSettleDTO;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.OnlyCreateServiceTmplate;
import com.example.revenueshare.core.service.ValidateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevnSettleService extends OnlyCreateServiceTmplate<ResponseVO, String, RevnSettleDTO, ChannelRsMastIds> {


    private final ChannelRsMastRepository channelRsMastRepository;
    private final ChannelRepository channelRepository;
    private final ChannelRevnRepository channelRevnRepository;
    private final ContractCmpnyRepository contractCmpnyRepository;
    private final ContractCreatorRepository contractCreatorRepository;
    private final ChannelRevnMastCmpMngService channelRevnMastCmpMngService;
    private final ChannelRevnMastCrtMngService channelRevnMastCrtMngService;



    @Override
    protected void addProc(RevnSettleDTO dto) {
        /* =================================================
        * 채널 월 총수익금 등록
        ================================================= */
        ChannelRsMast channelRsMast = ChannelRsMast.builder()
                .channel(Channel.builder().channelId(dto.getChannelId()).build())
                .calYm(dto.getCalYm())
                .totAmt(this.findTotAmtByChannelIdAndCalYm(dto.getChannelId(), dto.getCalYm()))
                .build();

        /* =================================================
        * 채널 수익금 등록
        *   -. 회사와 수익배분 및 잔액을 구한다.
        ================================================= */
        Long totRsAmt = contractCmpnyRepository.findAllByChannel(Channel.builder().channelId(dto.getChannelId()).build())
                .stream()
                .map(data -> rsCmpny(data, dto.getCalYm(), channelRsMast.getTotAmt()))
                .reduce(Long::sum)
                .orElse(0L);
        channelRsMast.setChannelAmt(channelRsMast.getTotAmt() - totRsAmt);

        /* =================================================
        * 잔액 등록
        *   -. 크리에이터와 수익배분 및 잔액을 등록 한다.
        ================================================= */
        totRsAmt = contractCreatorRepository.findAllByChannel(Channel.builder().channelId(dto.getChannelId()).build())
                .stream()
                .map(data -> rsCreator(data, dto.getCalYm(), channelRsMast.getChannelAmt()))
                .reduce(Long::sum)
                .orElse(0L);
        channelRsMast.setBalanceAmt(channelRsMast.getChannelAmt() - totRsAmt);


        /* =================================================
        * save
        ================================================= */
        channelRsMastRepository.save(channelRsMast);
    }

    @Override
    protected void validate(RevnSettleDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("등록되지 않은 채널(%d) 입니다", dto.getChannelId())));
        if (type.equals(ValidateType.C))
            channelRsMastRepository.findById(ChannelRsMastIds.builder().channel(dto.getChannelId()).calYm(dto.getCalYm()).build())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, String.format("%s 채널의 %s년%s월 수익정산이 등록되어 있습니다.", data.getChannel().getChannelNm(), data.getCalYm().substring(0, 4), data.getCalYm().substring(4)));
                    });
    }


    private Long findTotAmtByChannelIdAndCalYm(Long channelId, String calYm) {
        Channel channel = Channel.builder().channelId(channelId).build();
        return channelRevnRepository.findAllByChannelIdAndRevnDeLikeAndDelYn(channelId, calYm, "N")
                .stream()
                .map(data -> data.getRevnAmt())
                .reduce(Long::sum)
                .orElse(0L);
    }

    private Long rsCmpny(ContractCmpny cntrt, String calYm, Long totAmt) {
        try {
            Long calAmt = AmtCalUtil.amtByRate(totAmt, cntrt.getRsRate());

            channelRevnMastCmpMngService.add(ChannelRevnMastCmpDTO.builder()
                    .cntrCmpId(cntrt.getCntrCmpId())
                    .calYm(calYm)
                    .calAmt(calAmt)
                    .build());

            return calAmt;

        } catch (ArithmeticException e) {
            throw new RsException(ErrCd.ERR502, String.format("%s 회사의 %s 채널 %s년%s월 수익정산 금액 계산 실패. [총수익: %d 분배비율: %d%%]", cntrt.getCmpny().getCmpnyNm(), cntrt.getChannel().getChannelNm(), calYm.substring(0, 4), calYm.substring(4), totAmt, cntrt.getRsRate()), e);
        }

    }

    private Long rsCreator(ContractCreator cntrt, String calYm, Long channelAmt) {
        try {
            Long calAmt = AmtCalUtil.amtByRate(channelAmt, cntrt.getRsRate());
            channelRevnMastCrtMngService.add(ChannelRevnMastCrtDTO.builder()
                    .cntrCrtId(cntrt.getCntrCrtId())
                    .calYm(calYm)
                    .calAmt(calAmt)
                    .build());

            return calAmt;

        } catch (ArithmeticException e) {
            throw new RsException(ErrCd.ERR502, String.format("%s 크리에이터의 %s 채널 %s년%s월 수익정산 금액 계산 실패. [채널수익: %d 분배비율: %d%%]", cntrt.getCreator().getCreatorNm(), cntrt.getChannel().getChannelNm(), calYm.substring(0, 4), calYm.substring(4), channelAmt, cntrt.getRsRate()), e);
        }


    }

}
