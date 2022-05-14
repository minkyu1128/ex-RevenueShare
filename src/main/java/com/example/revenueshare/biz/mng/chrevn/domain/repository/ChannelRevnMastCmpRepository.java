package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRevenueMastCmpIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCmpRepository extends JpaRepository<ChannelRevnMastCmp, ChannelRevenueMastCmpIds>, ChannelRevnMastCmpRepositoryCustom {
}
