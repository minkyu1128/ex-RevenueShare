package com.example.revenueshare.ctgy.base.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.base.domain.Channel;
import com.example.revenueshare.ctgy.base.model.ChannelDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelMapper extends GenericMapper<ChannelDTO, Channel> {
}
