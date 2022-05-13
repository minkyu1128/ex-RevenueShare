package com.example.revenueshare.biz.mng.cntrt.model.mapstruct;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = StructMapperConfig.class)
public interface ContractCreatorMapper extends GenericMapper<ContractCreatorDTO, ContractCreator> {

    @Override
    @Mapping(target = "creator.creatorId", source = "creatorId")
    @Mapping(target = "channel.channelId", source = "channelId")
    ContractCreator toEntity(ContractCreatorDTO contractCreatorDTO);

    @Override
    @Mapping(target = "creatorId", expression = "java(contractCreator.getCreator().getCreatorId())")
    @Mapping(target = "channelId", expression = "java(contractCreator.getChannel().getChannelId())")
    ContractCreatorDTO toDto(ContractCreator contractCreator);
}
