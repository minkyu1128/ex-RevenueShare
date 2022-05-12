package com.example.revenueshare.biz.mng.revn.service;

import com.example.revenueshare.biz.mng.revn.domain.repository.ChannelRsMastRepository;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.biz.mng.revn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRsMastIds;
import com.example.revenueshare.biz.mng.revn.model.ChannelRsMastDTO;
import com.example.revenueshare.biz.mng.revn.model.ChannelRsMastSearchDTO;
import com.example.revenueshare.biz.mng.revn.model.mapstruct.ChannelRsMastMapper;
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
public class ChannelRsMastMngService extends CrudServiceTmplate<ResponseVO, ChannelRsMastSearchDTO, ChannelRsMastDTO, ChannelRsMastIds> {

    private final ChannelRsMastRepository channelRsMastRepository;

    private ChannelRsMastMapper mapper = Mappers.getMapper(ChannelRsMastMapper.class);

    @Override
    public ResponseVO<List<ChannelRsMastDTO>> findAllBy(ChannelRsMastSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRsMast> channelRsMasts = channelRsMastRepository.findAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelRsMastDTO> resultInfo = channelRsMasts.size() == 0 ?
                new ArrayList<ChannelRsMastDTO>() :
                channelRsMasts.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelRsMastDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelRsMastDTO> findById(ChannelRsMastIds id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRsMast channelRsMast = channelRsMastRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRsMastDTO resultInfo = mapper.toDto(channelRsMast);

        return ResponseVO.<ChannelRsMastDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(ChannelRsMastDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRsMast channelRsMast = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        channelRsMastRepository.save(channelRsMast);
    }

    @Override
    public void modify(ChannelRsMastDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRsMast channelRsMast = channelRsMastRepository.findById(ChannelRsMastIds.builder()
                        .channel(dto.getChannelId())
                        .calYm(dto.getCalYm())
                        .build())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channelRsMast);
    }
}
