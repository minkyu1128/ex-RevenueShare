package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRevenueMastCmpIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCmpRepository extends JpaRepository<ChannelRevnMastCmp, ChannelRevenueMastCmpIds>, ChannelRevnMastCmpRepositoryCustom {
}
