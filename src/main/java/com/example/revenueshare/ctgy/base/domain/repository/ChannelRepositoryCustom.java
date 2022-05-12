package com.example.revenueshare.ctgy.base.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Channel;
import com.example.revenueshare.ctgy.base.model.ChannelSearchDTO;

import java.util.List;

public interface ChannelRepositoryCustom {

    List<Channel> findAllByDto(ChannelSearchDTO searchDTO);
}
