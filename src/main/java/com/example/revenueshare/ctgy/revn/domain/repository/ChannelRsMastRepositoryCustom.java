package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRsMast;
import com.example.revenueshare.ctgy.revn.model.ChannelRsMastSearchDTO;

import java.util.List;

public interface ChannelRsMastRepositoryCustom {

    List<ChannelRsMast> findAllByDto(ChannelRsMastSearchDTO searchDTO);
}
