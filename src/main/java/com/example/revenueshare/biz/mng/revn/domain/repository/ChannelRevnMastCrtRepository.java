package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRevenueMastCrtIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnMastCrtRepository extends JpaRepository<ChannelRevnMastCrt, ChannelRevenueMastCrtIds>, ChannelRevnMastCrtRepositoryCustom {
}
