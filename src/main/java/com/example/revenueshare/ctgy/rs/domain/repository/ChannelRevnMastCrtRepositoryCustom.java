package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ChannelRevnMastCrt;
import com.example.revenueshare.ctgy.rs.model.ChannelRevnMastCrtSearchDTO;

import java.util.List;

public interface ChannelRevnMastCrtRepositoryCustom {

    List<ChannelRevnMastCrt> findAllByDto(ChannelRevnMastCrtSearchDTO searchDTO);
}
