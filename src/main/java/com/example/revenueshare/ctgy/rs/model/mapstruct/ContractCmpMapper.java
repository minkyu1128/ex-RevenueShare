package com.example.revenueshare.ctgy.rs.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.rs.domain.ContractCmpny;
import com.example.revenueshare.ctgy.rs.model.ContractCmpnyDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ContractCmpMapper extends GenericMapper<ContractCmpnyDTO, ContractCmpny> {
}
