package com.example.revenueshare.biz.mng.revn.model.mapstruct;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRevnMapper extends GenericMapper<ChannelRevnDTO, ChannelRevn> {
}
