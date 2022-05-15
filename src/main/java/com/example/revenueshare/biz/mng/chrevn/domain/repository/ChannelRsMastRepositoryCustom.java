package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRsMastSearchDTO;
import com.example.revenueshare.biz.revnsett.model.RevnFndSearchDTO;


import java.util.List;
import java.util.Map;

public interface ChannelRsMastRepositoryCustom {

    List<ChannelRsMast> findFetchAllByDto(ChannelRsMastSearchDTO searchDTO);


    List<Map<String, Object>> findRevnSettleBySearchDto(RevnFndSearchDTO searchDTO);
}
