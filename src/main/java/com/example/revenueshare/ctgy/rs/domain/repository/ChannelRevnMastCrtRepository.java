package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ChannelRevnMastCrt;
import com.example.revenueshare.ctgy.rs.domain.ids.ChannelRevenueMastCrtIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCrtRepository extends JpaRepository<ChannelRevnMastCrt, ChannelRevenueMastCrtIds>, ChannelRevnMastCrtRepositoryCustom {
}
