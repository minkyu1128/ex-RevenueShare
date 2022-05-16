package com.example.revenueshare.biz.revn.service;

import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnMastCmpRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnMastCrtRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRsMastRepository;
import com.example.revenueshare.biz.revn.model.RevnFndSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RevnFndService {

    private final ChannelRsMastRepository channelRsMastRepository;
    private final ChannelRevnMastCmpRepository channelRevnMastCmpRepository;
    private final ChannelRevnMastCrtRepository channelRevnMastCrtRepository;

    public List<Map<String, Object>> findAllByCh(RevnFndSearchDTO searchDTO) {
        return channelRsMastRepository.findRevnSettleBySearchDto(searchDTO);
    }

    public List<Map<String, Object>> findAllByCrt(RevnFndSearchDTO searchDTO) {
        return channelRevnMastCrtRepository.findRevnSettleBySearchDto(searchDTO);
    }

    public List<Map<String, Object>> findAllByCmpny(RevnFndSearchDTO searchDTO) {
        return channelRevnMastCmpRepository.findRevnSettleBySearchDto(searchDTO);
    }

    public List<Map<String, Object>> findAllSumByCmpny(RevnFndSearchDTO searchDTO) {
        return channelRevnMastCmpRepository.findRevnSettleSumBySearchDto(searchDTO);
    }

}
