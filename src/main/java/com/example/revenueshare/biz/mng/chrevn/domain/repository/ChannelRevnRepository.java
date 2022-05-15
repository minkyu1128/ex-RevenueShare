package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnRepository extends JpaRepository<ChannelRevn, Long>, ChannelRevnRepositoryCustom {

}
