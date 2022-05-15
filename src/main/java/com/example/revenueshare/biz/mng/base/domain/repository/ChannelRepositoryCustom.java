package com.example.revenueshare.biz.mng.base.domain.repository;

import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.model.ChannelSearchDTO;

import java.util.List;

public interface ChannelRepositoryCustom {

    List<Channel> findFetchAllByDto(ChannelSearchDTO searchDTO);
}
