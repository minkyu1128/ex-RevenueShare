package com.example.revenueshare.biz.mng.chrevn.model.mapstruct;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCrtDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMastCrtMapper extends GenericMapper<ChannelRevnMastCrtDTO, ChannelRevnMastCrt> {

    @Override
    @Mapping(target = "contractCreator.cntrCrtId", source = "cntrCrtId")
    ChannelRevnMastCrt toEntity(ChannelRevnMastCrtDTO channelRevnMastCrtDTO);

    @Override
    @Mapping(target = "cntrCrtId", expression = "java(channelRevnMastCrt.getContractCreator().getCntrCrtId())")
    ChannelRevnMastCrtDTO toDto(ChannelRevnMastCrt channelRevnMastCrt);
}
