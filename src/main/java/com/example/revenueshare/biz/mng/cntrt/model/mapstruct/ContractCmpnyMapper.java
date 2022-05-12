package com.example.revenueshare.biz.mng.cntrt.model.mapstruct;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnyDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ContractCmpnyMapper extends GenericMapper<ContractCmpnyDTO, ContractCmpny> {
}
