package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.revn.model.ChannelRsMastSearchDTO;

import java.util.List;

public interface ChannelRsMastRepositoryCustom {

    List<ChannelRsMast> findAllByDto(ChannelRsMastSearchDTO searchDTO);
}
