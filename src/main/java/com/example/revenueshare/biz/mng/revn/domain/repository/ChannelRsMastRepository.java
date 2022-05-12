package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.revn.domain.ids.ChannelRsMastIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRsMastRepository extends JpaRepository<ChannelRsMast, ChannelRsMastIds>, ChannelRsMastRepositoryCustom {
}
