package com.example.revenueshare.ctgy.rs.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.rs.domain.Channel;
import com.example.revenueshare.ctgy.rs.domain.ContractCreator;
import com.example.revenueshare.ctgy.rs.model.ChannelDTO;
import com.example.revenueshare.ctgy.rs.model.ContractCrtDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ContractCrtMapper extends GenericMapper<ContractCrtDTO, ContractCreator> {
}
