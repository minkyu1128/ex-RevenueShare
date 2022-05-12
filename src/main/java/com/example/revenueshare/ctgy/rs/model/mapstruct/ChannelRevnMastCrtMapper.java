package com.example.revenueshare.ctgy.rs.model.mapstruct;

import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import com.example.revenueshare.ctgy.rs.domain.ChannelRevnMastCrt;
import com.example.revenueshare.ctgy.rs.model.ChannelRevnMastCrtDTO;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMastCrtMapper extends GenericMapper<ChannelRevnMastCrtDTO, ChannelRevnMastCrt> {
}
