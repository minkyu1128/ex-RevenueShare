package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRevenueMastCrtIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCrtRepository extends JpaRepository<ChannelRevnMastCrt, ChannelRevenueMastCrtIds>, ChannelRevnMastCrtRepositoryCustom {
}
