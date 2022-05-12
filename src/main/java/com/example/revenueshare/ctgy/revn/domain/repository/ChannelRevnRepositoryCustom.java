package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRevn;
import com.example.revenueshare.ctgy.revn.model.ChannelRevnSearchDTO;

import java.util.List;

public interface ChannelRevnRepositoryCustom {

    List<ChannelRevn> findAllByDto(ChannelRevnSearchDTO searchDTO);
}
