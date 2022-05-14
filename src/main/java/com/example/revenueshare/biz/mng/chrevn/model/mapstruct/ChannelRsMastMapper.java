package com.example.revenueshare.biz.mng.chrevn.model.mapstruct;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRsMastDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRsMastMapper extends GenericMapper<ChannelRsMastDTO, ChannelRsMast> {

    @Override
    @Mapping(target = "channel.channelId", source = "channelId")
    ChannelRsMast toEntity(ChannelRsMastDTO channelRsMastDTO);

    @Override
    @Mapping(target = "channelId", expression = "java(channelRsMast.getChannel().getChannelId())")
    ChannelRsMastDTO toDto(ChannelRsMast channelRsMast);
}
