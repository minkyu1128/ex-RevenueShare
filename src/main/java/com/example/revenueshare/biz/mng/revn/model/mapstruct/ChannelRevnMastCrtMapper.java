package com.example.revenueshare.biz.mng.revn.model.mapstruct;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCrtDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMastCrtMapper extends GenericMapper<ChannelRevnMastCrtDTO, ChannelRevnMastCrt> {
}
