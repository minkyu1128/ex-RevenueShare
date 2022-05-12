package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCrtSearchDTO;

import java.util.List;

public interface ChannelRevnMastCrtRepositoryCustom {

    List<ChannelRevnMastCrt> findAllByDto(ChannelRevnMastCrtSearchDTO searchDTO);
}
