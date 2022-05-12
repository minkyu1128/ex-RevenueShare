package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnRepository extends JpaRepository<ChannelRevn, Long>, ChannelRevnRepositoryCustom {
}
