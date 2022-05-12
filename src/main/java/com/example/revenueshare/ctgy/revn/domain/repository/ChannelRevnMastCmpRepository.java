package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.ctgy.revn.domain.ids.ChannelRevenueMastCmpIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCmpRepository extends JpaRepository<ChannelRevnMastCmp, ChannelRevenueMastCmpIds>, ChannelRevnMastCmpRepositoryCustom {
}
