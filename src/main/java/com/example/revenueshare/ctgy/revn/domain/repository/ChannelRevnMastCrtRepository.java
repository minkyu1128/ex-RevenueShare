package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.ctgy.revn.domain.ids.ChannelRevenueMastCrtIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCrtRepository extends JpaRepository<ChannelRevnMastCrt, ChannelRevenueMastCrtIds>, ChannelRevnMastCrtRepositoryCustom {
}
