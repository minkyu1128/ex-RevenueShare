package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.Channel;
import com.example.revenueshare.ctgy.rs.model.ChannelDTO;
import com.example.revenueshare.ctgy.rs.model.ChannelSearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepositoryCustom {

    List<Channel> findAllByDto(ChannelSearchDTO searchDTO);
}
