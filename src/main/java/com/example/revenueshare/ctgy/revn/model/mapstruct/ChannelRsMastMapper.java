package com.example.revenueshare.ctgy.revn.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.revn.domain.ChannelRsMast;
import com.example.revenueshare.ctgy.revn.model.ChannelRsMastDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRsMastMapper extends GenericMapper<ChannelRsMastDTO, ChannelRsMast> {
}
