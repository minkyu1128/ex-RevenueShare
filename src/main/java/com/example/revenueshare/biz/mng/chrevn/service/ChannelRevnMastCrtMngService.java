package com.example.revenueshare.biz.mng.chrevn.service;

import com.example.revenueshare.biz.mng.cntrt.domain.repository.ContractCreatorRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRevenueMastCrtIds;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnMastCrtRepository;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCrtDTO;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCrtSearchDTO;
import com.example.revenueshare.biz.mng.chrevn.model.mapstruct.ChannelRevnMastCrtMapper;
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
public class ChannelRevnMastCrtMngService extends CrudValidServiceTmplate<ResponseVO, ChannelRevnMastCrtSearchDTO, ChannelRevnMastCrtDTO, ChannelRevenueMastCrtIds> {

    private final ChannelRevnMastCrtRepository channelRevnMastCrtRepository;
    private final ContractCreatorRepository contractCreatorRepository;

    private ChannelRevnMastCrtMapper mapper = Mappers.getMapper(ChannelRevnMastCrtMapper.class);

    @Override
    public ResponseVO<List<ChannelRevnMastCrtDTO>> findAllBy(ChannelRevnMastCrtSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRevnMastCrt> channelRevnMastCrts = channelRevnMastCrtRepository.findFetchAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelRevnMastCrtDTO> resultInfo = channelRevnMastCrts.size() == 0 ?
                new ArrayList<ChannelRevnMastCrtDTO>() :
                channelRevnMastCrts.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelRevnMastCrtDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelRevnMastCrtDTO> findById(ChannelRevenueMastCrtIds id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCrt channelRevnMastCrt = channelRevnMastCrtRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRevnMastCrtDTO resultInfo = mapper.toDto(channelRevnMastCrt);

        return ResponseVO.<ChannelRevnMastCrtDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    protected void validate(ChannelRevnMastCrtDTO dto, ValidateType type) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validation(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        contractCreatorRepository.findById(dto.getCntrCrtId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, String.format("등록되지 않은 계약정보(%d) 입니다.", dto.getCntrCrtId())));
        if (type.equals(ValidateType.C))
            channelRevnMastCrtRepository.findById(ChannelRevenueMastCrtIds.builder()
                            .contractCreator(dto.getCntrCrtId())
                            .calYm(dto.getCalYm())
                            .build())
                    .ifPresent(data -> {
                        throw new RsException(ErrCd.ERR401, String.format("%s 채널의 %s년%월 수익이 등록되어 있습니다.", data.getContractCreator().getChannel().getChannelNm(), data.getCalYm().substring(0, 4), data.getCalYm().substring(4)));
                    });

    }

    @Override
    protected ResponseVO<ChannelRevenueMastCrtIds> addProc(ChannelRevnMastCrtDTO dto) {

        /* ======================================================
         * conversion
         ====================================================== */
        ChannelRevnMastCrt channelRevnMastCrt = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        channelRevnMastCrtRepository.save(channelRevnMastCrt);


        return ResponseVO.<ChannelRevenueMastCrtIds>okBuilder().resultInfo(ChannelRevenueMastCrtIds.builder()
                        .contractCreator(channelRevnMastCrt.getContractCreator().getCntrCrtId())
                        .calYm(channelRevnMastCrt.getCalYm())
                        .build())
                .build();
    }

    @Override
    protected void modifyProc(ChannelRevnMastCrtDTO dto) {

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCrt channelRevnMastCrt = channelRevnMastCrtRepository.findById(ChannelRevenueMastCrtIds.builder()
                        .contractCreator(dto.getCntrCrtId())
                        .calYm(dto.getCalYm())
                        .build())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channelRevnMastCrt);
    }
}
