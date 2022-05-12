package com.example.revenueshare.biz.mng.base.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.biz.mng.base.domain.Creator;
import com.example.revenueshare.biz.mng.base.model.CreatorDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface CreatorMapper extends GenericMapper<CreatorDTO, Creator> {
}
