package com.example.revenueshare.ctgy.rs.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.exception.RsException;
import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.ctgy.rs.domain.Channel;
import com.example.revenueshare.ctgy.rs.domain.repository.ChannelRepository;
import com.example.revenueshare.ctgy.rs.model.ChannelDTO;
import com.example.revenueshare.ctgy.rs.model.mapstruct.ChannelMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChannelMngService extends CrudServiceTmplate<ResponseVO, ChannelDTO, Long> {

    private final ChannelRepository channelRepository;

    private ChannelMapper mapper = Mappers.getMapper(ChannelMapper.class);

    @Override
    public ResponseVO<List<ChannelDTO>> findAllBy(ChannelDTO channelDTO) {

        List<Channel> channels = channelRepository.findAllByDto(channelDTO);

        List<ChannelDTO> resultInfo = channels.size() == 0 ?
                new ArrayList<ChannelDTO>() :
                channels.stream()
                        .map(row -> mapper.toDto(row))
                        .collect(Collectors.toList());

        return ResponseVO.<List<ChannelDTO>>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public ResponseVO<ChannelDTO> findById(Long id) {

        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        ChannelDTO resultInfo = mapper.toDto(channel);

        return ResponseVO.<ChannelDTO>okBuilder().resultInfo(resultInfo).build();
    }

    @Override
    public void add(ChannelDTO channelDTO) {
        ResponseVO validate = validate(channelDTO);
        if (!ErrCd.OK.equals(validate.getErrCd()))
            throw new RsException(validate.getErrCd(), validate.getErrMsg());

        Channel channel = mapper.toEntity(channelDTO);
        channel.setCloseDe(null);
        channel.setUseYn("Y");

        channelRepository.save(channel);

    }

    @Override
    public void modify(ChannelDTO channelDTO) {

        Channel channel = channelRepository.findById(channelDTO.getChannelId())
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        mapper.updateFromDto(channelDTO, channel);
        channel.setUseYn("Y");

    }

    @Override
    public void removeById(Long id) {

        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new RsException(ErrCd.ERR401, "일치하는 자료가 없습니다."));

        channel.setUseYn("N");

    }
}
