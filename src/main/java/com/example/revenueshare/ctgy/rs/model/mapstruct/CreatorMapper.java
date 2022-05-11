package com.example.revenueshare.ctgy.rs.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.rs.domain.Creator;
import com.example.revenueshare.ctgy.rs.model.CreatorDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface CreatorMapper extends GenericMapper<CreatorDTO, Creator> {
}
