package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnSearchDTO;

import java.util.List;

public interface ChannelRevnRepositoryCustom {

    List<ChannelRevn> findAllByDto(ChannelRevnSearchDTO searchDTO);
}
