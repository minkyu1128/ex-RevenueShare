package com.example.revenueshare.biz.mng.revn.service;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCrtDTO;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCrtSearchDTO;
import com.example.revenueshare.biz.mng.revn.model.mapstruct.ChannelRevnMastCrtMapper;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRevenueMastCrtIds;
import com.example.revenueshare.biz.mng.revn.domain.repository.ChannelRevnMastCrtRepository;
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
public class ChannelRevnMastCrtMngService extends CrudServiceTmplate<ResponseVO, ChannelRevnMastCrtSearchDTO, ChannelRevnMastCrtDTO, ChannelRevenueMastCrtIds> {

    private final ChannelRevnMastCrtRepository channelRevnMastCrtRepository;

    private ChannelRevnMastCrtMapper mapper = Mappers.getMapper(ChannelRevnMastCrtMapper.class);

    @Override
    public ResponseVO<List<ChannelRevnMastCrtDTO>> findAllBy(ChannelRevnMastCrtSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRevnMastCrt> channelRevnMastCrts = channelRevnMastCrtRepository.findAllByDto(searchDTO);

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
    public void add(ChannelRevnMastCrtDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        channelRevnMastCrtRepository.findById(ChannelRevenueMastCrtIds.builder()
                        .contractCreator(dto.getCntrCrtId())
                        .calYm(dto.getCalYm())
                        .build())
                .ifPresent(data -> {
                    throw new RsException(ErrCd.ERR401, String.format("%s 채널의 수익(%)이 등록되어 있습니다.", data.getContractCreator().getChannel().getChannelNm(), data.getCalYm()));
                });

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCrt channelRevnMastCrt = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        channelRevnMastCrtRepository.save(channelRevnMastCrt);
    }

    @Override
    public void modify(ChannelRevnMastCrtDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

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
