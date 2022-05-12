package com.example.revenueshare.biz.mng.revn.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRevenueMastCmpIds;
import com.example.revenueshare.biz.mng.revn.domain.repository.ChannelRevnMastCmpRepository;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCmpDTO;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCmpSearchDTO;
import com.example.revenueshare.biz.mng.revn.model.mapstruct.ChannelRevnMastCmpMapper;
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
public class ChannelRevnMastCmpMngService extends CrudServiceTmplate<ResponseVO, ChannelRevnMastCmpSearchDTO, ChannelRevnMastCmpDTO, ChannelRevenueMastCmpIds> {

    private final ChannelRevnMastCmpRepository channelRevnMastCmpRepository;

    private ChannelRevnMastCmpMapper mapper = Mappers.getMapper(ChannelRevnMastCmpMapper.class);

    @Override
    public ResponseVO<List<ChannelRevnMastCmpDTO>> findAllBy(ChannelRevnMastCmpSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRevnMastCmp> channelRevnMastCmps = channelRevnMastCmpRepository.findAllByDto(searchDTO);

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
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRevnMastCmpDTO resultInfo = mapper.toDto(channelRevnMastCmp);

        return ResponseVO.<ChannelRevnMastCmpDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(ChannelRevnMastCmpDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        channelRevnMastCmpRepository.findById(ChannelRevenueMastCmpIds.builder()
                        .contractCmpny(dto.getCntrCmpId())
                        .calYm(dto.getCalYm())
                        .build())
                .ifPresent(data -> {
                    throw new RsException(ErrCd.ERR401, String.format("%s 채널의 수익(%)이 등록되어 있습니다.", data.getContractCmpny().getChannel().getChannelNm(), data.getCalYm()));
                });

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCmp channelRevnMastCmp = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        channelRevnMastCmpRepository.save(channelRevnMastCmp);
    }

    @Override
    public void modify(ChannelRevnMastCmpDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevnMastCmp channelRevnMastCmp = channelRevnMastCmpRepository.findById(ChannelRevenueMastCmpIds.builder()
                        .contractCmpny(dto.getCntrCmpId())
                        .calYm(dto.getCalYm())
                        .build())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channelRevnMastCmp);
    }
}
