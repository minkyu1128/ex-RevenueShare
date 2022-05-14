package com.example.revenueshare.biz.mng.chrevn.model.mapstruct;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMapper extends GenericMapper<ChannelRevnDTO, ChannelRevn> {

    @Override
    @Mapping(target = "channel.channelId", source = "channelId")
    @Mapping(target = "revnSeCd", expression = "java(RevnSeCd.valueOf(channelRevnDTO.getRevnSeCd()))")
    ChannelRevn toEntity(ChannelRevnDTO channelRevnDTO);


    @Override
    @Mapping(target = "channelId", expression = "java(channelRevn.getChannel().getChannelId())")
    @Mapping(target = "revnSeCd", expression = "java(channelRevn.getRevnSeCd().getCode())")
    ChannelRevnDTO toDto(ChannelRevn channelRevn);
}
