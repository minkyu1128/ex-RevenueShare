package com.example.revenueshare.biz.mng.revn.model.mapstruct;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCmpDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMastCmpMapper extends GenericMapper<ChannelRevnMastCmpDTO, ChannelRevnMastCmp> {
}
