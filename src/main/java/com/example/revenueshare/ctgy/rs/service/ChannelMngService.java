package com.example.revenueshare.ctgy.rs.service;

import com.example.revenueshare.ctgy.rs.domain.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelMngService {

    private final ChannelRepository channelRepository;
}
