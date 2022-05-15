package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCmpSearchDTO;
import com.example.revenueshare.biz.revnsett.model.RevnFndSearchDTO;


import java.util.List;
import java.util.Map;

public interface ChannelRevnMastCmpRepositoryCustom {

    List<ChannelRevnMastCmp> findFetchAllByDto(ChannelRevnMastCmpSearchDTO searchDTO);
    List<Map<String, Object>> findRevnSettleBySearchDto(RevnFndSearchDTO searchDTO);
}
