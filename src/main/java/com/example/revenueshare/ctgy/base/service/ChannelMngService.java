package com.example.revenueshare.ctgy.base.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.ctgy.base.domain.Channel;
import com.example.revenueshare.ctgy.base.domain.repository.ChannelRepository;
import com.example.revenueshare.ctgy.base.model.ChannelDTO;
import com.example.revenueshare.ctgy.base.model.ChannelSearchDTO;
import com.example.revenueshare.ctgy.base.model.mapstruct.ChannelMapper;
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
public class ChannelMngService extends CrudServiceTmplate<ResponseVO, ChannelSearchDTO, ChannelDTO, Long> {

    private final ChannelRepository channelRepository;

    private ChannelMapper mapper = Mappers.getMapper(ChannelMapper.class);

    @Override
    public ResponseVO<List<ChannelDTO>> findAllBy(ChannelSearchDTO searchDTO) {
        /* ======================================================
         * find data
         ====================================================== */
        List<Channel> channels = channelRepository.findAllByDto(searchDTO);

        /* ======================================================
         * mapping
         ====================================================== */
        List<ChannelDTO> resultInfo = channels.size() == 0 ?
                new ArrayList<ChannelDTO>() :
                channels.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelDTO> findById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * mapping
         ====================================================== */
        ChannelDTO resultInfo = mapper.toDto(channel);

        return ResponseVO.<ChannelDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(ChannelDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());
        channelRepository.findByChannelNm(dto.getChannelNm())
                .ifPresent(data -> {
                    throw new RsException(ErrCd.ERR401, "동일한 채널명("+data.getChannelNm()+")이 등록되어 있습니다.");
                });

        /* ======================================================
         * find data
         ====================================================== */
        Channel channel = mapper.toEntity(dto);

        /* ======================================================
         * save
         ====================================================== */
        channel.setCloseDe(null);
        channel.setUseYn("Y");
        channelRepository.save(channel);
    }

    @Override
    public void modify(ChannelDTO dto) {
        /* ======================================================
         * validate
         ====================================================== */
        ResponseVO validate = validate(dto);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg(), validate.getResultInfo());

        /* ======================================================
         * find data
         ====================================================== */
        Channel channel = channelRepository.findById(dto.getChannelId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        mapper.updateFromDto(dto, channel);
        channel.setUseYn("Y");
    }

    @Override
    public void removeById(Long id) {
        /* ======================================================
         * find data
         ====================================================== */
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        /* ======================================================
         * save
         ====================================================== */
        channel.setUseYn("N");

    }
}
