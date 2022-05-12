package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ChannelRevnMastCmp;
import com.example.revenueshare.ctgy.rs.domain.ids.ChannelRevenueMastCmpIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCmpRepository extends JpaRepository<ChannelRevnMastCmp, ChannelRevenueMastCmpIds>, ChannelRevnMastCmpRepositoryCustom {
}
