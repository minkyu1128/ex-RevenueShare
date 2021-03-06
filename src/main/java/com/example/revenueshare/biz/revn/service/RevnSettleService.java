package com.example.revenueshare.biz.revn.service;

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
import com.example.revenueshare.biz.revn.model.RevnSettleDTO;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.OnlyCreateServiceTmplate;
import com.example.revenueshare.core.service.ValidateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    protected ResponseVO<ChannelRsMastIds> addProc(RevnSettleDTO dto) {
        /* =================================================
        * ?????? ??? ???????????? ??????
        ================================================= */
        ChannelRsMast channelRsMast = ChannelRsMast.builder()
                .channel(Channel.builder().channelId(dto.getChannelId()).build())
                .calYm(dto.getCalYm())
                .totAmt(this.findTotAmtByChannelIdAndCalYm(dto.getChannelId(), dto.getCalYm()))
                .build();

        /* =================================================
        * ?????? ????????? ??????
        *   -. ????????? ???????????? ??? ????????? ?????????.
        ================================================= */
        Long totRsAmt = contractCmpnyRepository.findAllByChannel(Channel.builder().channelId(dto.getChannelId()).build())
                .stream()
                .map(data -> rsCmpny(data, dto.getCalYm(), channelRsMast.getTotAmt()))
                .reduce(Long::sum)
                .orElse(0L);
        channelRsMast.setChannelAmt(channelRsMast.getTotAmt() - totRsAmt);

        /* =================================================
        * ?????? ??????
        *   -. ?????????????????? ???????????? ??? ????????? ?????? ??????.
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


        return ResponseVO.<ChannelRsMastIds>okBuilder().resultInfo(ChannelRsMastIds.builder()
                .channel(channelRsMast.getChannel().getChannelId())
                .calYm(channelRsMast.getCalYm())
                .build()).build();
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
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("???????????? ?????? ??????(%d) ?????????", dto.getChannelId())));
        if (type.equals(ValidateType.C)){
            channelRsMastRepository.findById(ChannelRsMastIds.builder().channel(dto.getChannelId()).calYm(dto.getCalYm()).build())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, String.format("%s ????????? %s???%s??? ??????????????? ???????????? ????????????.", data.getChannel().getChannelNm(), data.getCalYm().substring(0, 4), data.getCalYm().substring(4)));
                    });
            if(Integer.parseInt(dto.getCalYm()) >= Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"))))
                throw new RsException(ErrCd.ERR401, "????????? ??????????????? ????????? ?????? ?????????.");
        }
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
            throw new RsException(ErrCd.ERR502, String.format("%s ????????? %s ?????? %s???%s??? ???????????? ?????? ?????? ??????. [?????????: %d ????????????: %d%%]", cntrt.getCmpny().getCmpnyNm(), cntrt.getChannel().getChannelNm(), calYm.substring(0, 4), calYm.substring(4), totAmt, cntrt.getRsRate()), e);
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
            throw new RsException(ErrCd.ERR502, String.format("%s ?????????????????? %s ?????? %s???%s??? ???????????? ?????? ?????? ??????. [????????????: %d ????????????: %d%%]", cntrt.getCreator().getCreatorNm(), cntrt.getChannel().getChannelNm(), calYm.substring(0, 4), calYm.substring(4), channelAmt, cntrt.getRsRate()), e);
        }


    }

}
