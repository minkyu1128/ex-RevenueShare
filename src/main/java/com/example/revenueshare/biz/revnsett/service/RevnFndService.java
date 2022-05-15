package com.example.revenueshare.biz.revnsett.service;

import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnMastCmpRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRevnMastCrtRepository;
import com.example.revenueshare.biz.mng.chrevn.domain.repository.ChannelRsMastRepository;
import com.example.revenueshare.biz.revnsett.model.RevnFndSearchDTO;
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
        /*
         * 1. 특정 채널 기준 수익금액과 계약정보에 따른 크리에이터 정산금액 조회
            -. 조회: 기간별(월 기준) 조건 필수
         */
        return channelRsMastRepository.findRevnSettleBySearchDto(searchDTO);
    }

    public List<Map<String, Object>> findAllByCrt(RevnFndSearchDTO searchDTO) {
        /*
         * 2. 크리에이터 기준 채널별 정산금액 조회
            -. 조회: 기간별(월 기준) 조건 필수
         */
        return channelRevnMastCrtRepository.findRevnSettleBySearchDto(searchDTO);
    }

    public List<Map<String, Object>> findAllByCmpny(RevnFndSearchDTO searchDTO) {
        /*
         * 3. 월별 회사 총매출/순매출 조회
            -. 총매출: 채널 수익금액
            -. 순매출: 채널수익에서 계약된 회사 RS 금액
         */
        return channelRevnMastCmpRepository.findRevnSettleBySearchDto(searchDTO);
    }

}
