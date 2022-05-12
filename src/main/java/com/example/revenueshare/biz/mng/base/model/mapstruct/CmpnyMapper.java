package com.example.revenueshare.biz.mng.base.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.biz.mng.base.domain.Cmpny;
import com.example.revenueshare.biz.mng.base.model.CmpnyDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface CmpnyMapper extends GenericMapper<CmpnyDTO, Cmpny> {
}
