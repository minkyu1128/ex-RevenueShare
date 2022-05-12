package com.example.revenueshare.ctgy.rs.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.rs.domain.ChannelRevnMastCmp;
import com.example.revenueshare.ctgy.rs.model.ChannelRevnMastCmpDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMastCmpMapper extends GenericMapper<ChannelRevnMastCmpDTO, ChannelRevnMastCmp> {
}
