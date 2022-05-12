package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ChannelRsMast;
import com.example.revenueshare.ctgy.rs.model.ChannelRsMastSearchDTO;

import java.util.List;

public interface ChannelRsMastRepositoryCustom {

    List<ChannelRsMast> findAllByDto(ChannelRsMastSearchDTO searchDTO);
}
