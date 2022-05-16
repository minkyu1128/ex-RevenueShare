package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCrtSearchDTO;
import com.example.revenueshare.biz.revn.model.RevnFndSearchDTO;

import java.util.List;
import java.util.Map;

public interface ChannelRevnMastCrtRepositoryCustom {

    List<ChannelRevnMastCrt> findFetchAllByDto(ChannelRevnMastCrtSearchDTO searchDTO);
    List<Map<String, Object>> findRevnSettleBySearchDto(RevnFndSearchDTO searchDTO);
}
