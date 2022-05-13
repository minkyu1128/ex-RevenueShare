package com.example.revenueshare.biz.mng.cntrt.model.mapstruct;

import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import org.mapstruct.*;

@Mapper(config = StructMapperConfig.class)
public interface ContractCmpnyMapper extends GenericMapper<ContractCmpnyDTO, ContractCmpny> {

    @Override
    @Mapping(target = "cmpny.cmpnyId", source = "cmpnyId")
    @Mapping(target = "channel.channelId", source = "channelId")
    ContractCmpny toEntity(ContractCmpnyDTO contractCmpnyDTO);

    @Override
    @Mapping(target = "cmpnyId", expression = "java(contractCmpny.getCmpny().getCmpnyId())")
    @Mapping(target = "channelId", expression = "java(contractCmpny.getChannel().getChannelId())")
    ContractCmpnyDTO toDto(ContractCmpny contractCmpny);
}
