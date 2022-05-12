package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCmpSearchDTO;

import java.util.List;

public interface ChannelRevnMastCmpRepositoryCustom {

    List<ChannelRevnMastCmp> findAllByDto(ChannelRevnMastCmpSearchDTO searchDTO);
}
