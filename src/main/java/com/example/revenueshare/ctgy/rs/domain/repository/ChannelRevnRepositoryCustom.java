package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ChannelRevn;
import com.example.revenueshare.ctgy.rs.model.ChannelRevnSearchDTO;

import java.util.List;

public interface ChannelRevnRepositoryCustom {

    List<ChannelRevn> findAllByDto(ChannelRevnSearchDTO searchDTO);
}
