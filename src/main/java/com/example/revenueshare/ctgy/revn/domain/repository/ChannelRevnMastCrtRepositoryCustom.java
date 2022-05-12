package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.ctgy.revn.model.ChannelRevnMastCrtSearchDTO;

import java.util.List;

public interface ChannelRevnMastCrtRepositoryCustom {

    List<ChannelRevnMastCrt> findAllByDto(ChannelRevnMastCrtSearchDTO searchDTO);
}
