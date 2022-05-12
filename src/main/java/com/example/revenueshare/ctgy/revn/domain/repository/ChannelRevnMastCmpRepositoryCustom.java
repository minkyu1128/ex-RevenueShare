package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.ctgy.revn.model.ChannelRevnMastCmpSearchDTO;

import java.util.List;

public interface ChannelRevnMastCmpRepositoryCustom {

    List<ChannelRevnMastCmp> findAllByDto(ChannelRevnMastCmpSearchDTO searchDTO);
}
