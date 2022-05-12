package com.example.revenueshare.biz.mng.cntrt.model.mapstruct;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ContractCreatorMapper extends GenericMapper<ContractCreatorDTO, ContractCreator> {
}
