package com.example.revenueshare.biz.mng.revn.model.mapstruct;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMapper extends GenericMapper<ChannelRevnDTO, ChannelRevn> {

    @Override
    @Mapping(target = "revnSeCd", expression = "java(RevnSeCd.valueOf(channelRevnDTO.getRevnSeCd()))")
    ChannelRevn toEntity(ChannelRevnDTO channelRevnDTO);


    @Override
    @Mapping(target = "revnSeCd", expression = "java(channelRevn.getRevnSeCd().getCode())")
    ChannelRevnDTO toDto(ChannelRevn channelRevn);
}
