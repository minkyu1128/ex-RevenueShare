package com.example.revenueshare.ctgy.base.domain.repository;

import com.example.revenueshare.ctgy.base.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelRepositoryCustom {

    Optional<Channel> findByChannelNm(String channelNm);
}
