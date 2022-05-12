package com.example.revenueshare.biz.mng.revn.model.mapstruct;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.revn.model.ChannelRsMastDTO;
import com.example.revenueshare.core.mapstruct.GenericMapper;
import com.example.revenueshare.core.mapstruct.StructMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = StructMapperConfig.class)
public interface ChannelRsMastMapper extends GenericMapper<ChannelRsMastDTO, ChannelRsMast> {
}
