package com.example.revenueshare.ctgy.rs.service;

import com.example.revenueshare.core.model.ResponseVO;
import com.example.revenueshare.core.service.CrudServiceTmplate;
import com.example.revenueshare.ctgy.rs.domain.repository.ChannelRepository;
import com.example.revenueshare.ctgy.rs.model.ChannelDTO;
import com.example.revenueshare.ctgy.rs.model.mapstruct.ChannelMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelMngService extends CrudServiceTmplate<ResponseVO, ChannelDTO, Long> {

    private final ChannelRepository channelRepository;

    private ChannelMapper mapper = Mappers.getMapper(ChannelMapper.class);
    @Override
    public ResponseVO findAllBy(ChannelDTO channelDTO) {
        return null;
    }

    @Override
    public ResponseVO findById(Long id) {
        return null;
    }

    @Override
    public void add(ChannelDTO channelDTO) {

    }

    @Override
    public void modify(ChannelDTO channelDTO) {

    }
}
