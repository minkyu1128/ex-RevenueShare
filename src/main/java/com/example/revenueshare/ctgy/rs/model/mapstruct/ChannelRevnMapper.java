package com.example.revenueshare.ctgy.rs.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.rs.domain.ChannelRevn;
import com.example.revenueshare.ctgy.rs.model.ChannelRevnDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMapper extends GenericMapper<ChannelRevnDTO, ChannelRevn> {
}
