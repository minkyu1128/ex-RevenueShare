package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRsMastSearchDTO;

import java.util.List;

public interface ChannelRsMastRepositoryCustom {

    List<ChannelRsMast> findAllByDto(ChannelRsMastSearchDTO searchDTO);
}
