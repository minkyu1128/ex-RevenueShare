package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCrtSearchDTO;

import java.util.List;

public interface ChannelRevnMastCrtRepositoryCustom {

    List<ChannelRevnMastCrt> findAllByDto(ChannelRevnMastCrtSearchDTO searchDTO);
}
