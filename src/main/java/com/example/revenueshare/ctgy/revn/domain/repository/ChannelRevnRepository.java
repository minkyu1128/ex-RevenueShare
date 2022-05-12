package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRevn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRevnRepository extends JpaRepository<ChannelRevn, Long>, ChannelRevnRepositoryCustom {
}
