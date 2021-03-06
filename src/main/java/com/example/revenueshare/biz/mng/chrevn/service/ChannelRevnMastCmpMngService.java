package com.example.revenueshare.biz.mng.chrevn.service;

import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCmpnyRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRevenueMastCmpIds;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnMastCmpRepository;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCmpDTO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCmpSearchDTO;
import com.example.revenueshare.biz.mng.chrevn.model.mapstruct.ChannelRevnMastCmpMapper;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudValidServiceTmplate;
import com.example.revenueshare.core.service.ValidateType;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelRevnMastCmpMngService extends CrudValidServiceTmplate<ResponseVO, ChannelRevnMastCmpSearchDTO, ChannelRevnMastCmpDTO, ChannelRevenueMastCmpIds> {

    private final ChannelRevnMastCmpRepository channelRevnMastCmpRepository;
    private final ContractCmpnyRepository contractCmpnyRepository;

    private ChannelRevnMastCmpMapper mapper = Mappers.getMapper(ChannelRevnMastCmpMapper.class);

    @Override
    public ResponseVO<List<ChannelRevnMastCmpDTO>> findAllBy(ChannelRevnMastCmpSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRevnMastCmp> channelRevnMastCmps = channelRevnMastCmpRepository.findFetchAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelRevnMastCmpDTO> resultInfo = channelRevnMastCmps.size() == 0 ?
                new ArrayList<ChannelRevnMastCmpDTO>() :
                channelRevnMastCmps.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelRevnMastCmpDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelRevnMastCmpDTO> findById(ChannelRevenueMastCmpIds id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCmp channelRevnMastCmp = channelRevnMastCmpRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "???????????? ????????? ????????????."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRevnMastCmpDTO resultInfo = mapper.toDto(channelRevnMastCmp);

        return ResponseVO.<ChannelRevnMastCmpDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(ChannelRevnMastCmpDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        contractCmpnyRepository.findById(dto.getCntrCmpId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("???????????? ?????? ????????????(%d) ?????????.", dto.getCntrCmpId())));
        if (type.equals(ValidateType.C))
            channelRevnMastCmpRepository.findById(ChannelRevenueMastCmpIds.builder()
                            .contractCmpny(dto.getCntrCmpId())
                            .calYm(dto.getCalYm())
                            .build())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, String.format("%s ????????? %s???%s??? ????????? ???????????? ????????????.", data.getContractCmpny().getChannel().getChannelNm(), data.getCalYm().substring(0, 4), data.getCalYm().substring(4)));
                    });

    }

    @Override
    protected ResponseVO<ChannelRevenueMastCmpIds> addProc(ChannelRevnMastCmpDTO dto) {

        /* ======================================================
         * conversion
         ====================================================== */
        ChannelRevnMastCmp channelRevnMastCmp = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        channelRevnMastCmpRepository.save(channelRevnMastCmp);


        return ResponseVO.<ChannelRevenueMastCmpIds>okBuilder().resultInfo(ChannelRevenueMastCmpIds.builder()
                        .contractCmpny(channelRevnMastCmp.getContractCmpny().getCntrCmpId())
                        .calYm(channelRevnMastCmp.getCalYm())
                        .build())
                .build();
    }

    @Override
    protected void modifyProc(ChannelRevnMastCmpDTO dto) {

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCmp channelRevnMastCmp = channelRevnMastCmpRepository.findById(ChannelRevenueMastCmpIds.builder()
                        .contractCmpny(dto.getCntrCmpId())
                        .calYm(dto.getCalYm())
                        .build())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "???????????? ????????? ????????????."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channelRevnMastCmp);
    }
}
