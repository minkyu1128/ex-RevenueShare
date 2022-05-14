package com.example.revenueshare.biz.mng.chrevn.model.mapstruct;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCmpDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMastCmpMapper extends GenericMapper<ChannelRevnMastCmpDTO, ChannelRevnMastCmp> {
    @Override
    @Mapping(target = "contractCmpny.cntrCmpId", source = "cntrCmpId")
    ChannelRevnMastCmp toEntity(ChannelRevnMastCmpDTO channelRevnMastCmpDTO);

    @Override
    @Mapping(target = "cntrCmpId", expression = "java(channelRevnMastCmp.getContractCmpny().getCntrCmpId())")
    ChannelRevnMastCmpDTO toDto(ChannelRevnMastCmp channelRevnMastCmp);
}
