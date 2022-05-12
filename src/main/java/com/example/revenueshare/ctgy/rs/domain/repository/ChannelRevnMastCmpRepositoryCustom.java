package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ChannelRevnMastCmp;
import com.example.revenueshare.ctgy.rs.model.ChannelRevnMastCmpSearchDTO;

import java.util.List;

public interface ChannelRevnMastCmpRepositoryCustom {

    List<ChannelRevnMastCmp> findAllByDto(ChannelRevnMastCmpSearchDTO searchDTO);
}
