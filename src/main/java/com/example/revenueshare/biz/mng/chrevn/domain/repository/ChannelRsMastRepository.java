package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.chrevn.domain.ids.ChannelRsMastIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRsMastRepository extends JpaRepository<ChannelRsMast, ChannelRsMastIds>, ChannelRsMastRepositoryCustom {
}
