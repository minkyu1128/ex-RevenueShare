package com.example.revenueshare.biz.mng.revn.service;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.revn.domain.repository.ChannelRevnRepository;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnDTO;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnSearchDTO;
import com.example.revenueshare.biz.mng.revn.model.mapstruct.ChannelRevnMapper;
import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelRevnMngService extends CrudServiceTmplate<ResponseVO, ChannelRevnSearchDTO, ChannelRevnDTO, Long> {

    private final ChannelRevnRepository channelRevnRepository;

    private ChannelRevnMapper mapper = Mappers.getMapper(ChannelRevnMapper.class);

    @Override
    public ResponseVO<List<ChannelRevnDTO>> findAllBy(ChannelRevnSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<ChannelRevn> channelRevns = channelRevnRepository.findAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelRevnDTO> resultInfo = channelRevns.size() == 0 ?
                new ArrayList<ChannelRevnDTO>() :
                channelRevns.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelRevnDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelRevnDTO> findById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevn channelRevn = channelRevnRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelRevnDTO resultInfo = mapper.toDto(channelRevn);

        return ResponseVO.<ChannelRevnDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(ChannelRevnDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevn channelRevn = mapper.toEntity(dto);
        channelRevn.setRsYn("N");
        channelRevn.setDelYn("N");

        /* ======================================================
         * save
         ====================================================== */
        channelRevnRepository.save(channelRevn);
    }

    @Override
    public void modify(ChannelRevnDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevn channelRevn = channelRevnRepository.findById(dto.getChRevnId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channelRevn);
    }

    @Override
    public void removeById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        ChannelRevn channelRevn = channelRevnRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));
        if("Y".equals(channelRevn.getRsYn().toUpperCase(Locale.ROOT)))
            throw new RsException(ErrCd.ERR501, "정산이 완료되어 삭제가 불가 합니다.");

        /* ======================================================
         * save
         ====================================================== */
        channelRevn.setDelYn("Y");
        channelRevn.setDelDt(LocalDateTime.now());
    }
}
