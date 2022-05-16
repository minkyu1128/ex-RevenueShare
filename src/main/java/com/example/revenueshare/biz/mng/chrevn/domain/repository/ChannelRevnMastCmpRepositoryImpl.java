package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCmp;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCmpSearchDTO;
import com.example.revenueshare.biz.revn.model.RevnFndSearchDTO;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.revenueshare.biz.mng.base.domain.QChannel.channel;
import static com.example.revenueshare.biz.mng.chrevn.domain.QChannelRevnMastCmp.channelRevnMastCmp;
import static com.example.revenueshare.biz.mng.chrevn.domain.QChannelRevnMastCrt.channelRevnMastCrt;
import static com.example.revenueshare.biz.mng.chrevn.domain.QChannelRsMast.channelRsMast;
import static com.example.revenueshare.biz.mng.cntrt.domain.QContractCmpny.contractCmpny;
import static com.example.revenueshare.biz.mng.cntrt.domain.QContractCreator.contractCreator;
import static com.querydsl.core.group.GroupBy.groupBy;

@RequiredArgsConstructor
public class ChannelRevnMastCmpRepositoryImpl implements ChannelRevnMastCmpRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<ChannelRevnMastCmp> findFetchAllByDto(ChannelRevnMastCmpSearchDTO searchDTO) {
        return query.selectFrom(channelRevnMastCmp)
                .innerJoin(channelRevnMastCmp.contractCmpny, contractCmpny).fetchJoin()
                .innerJoin(contractCmpny.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
    }

    @Override
    public List<Map<String, Object>> findRevnSettleBySearchDto(RevnFndSearchDTO searchDTO) {
        return query.select(channelRevnMastCmp.contractCmpny.channel.channelId
                        , channelRevnMastCmp.contractCmpny.channel.channelNm
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyId
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyNm
                        , channelRevnMastCmp.calYm
                        , channelRsMast.totAmt
                        , channelRevnMastCmp.calAmt
                        , channelRevnMastCmp.contractCmpny.rsRate
                )
                .from(channelRevnMastCmp)
                .innerJoin(channelRsMast).on(channelRevnMastCmp.calYm.eq(channelRsMast.calYm)
                        .and(channelRevnMastCmp.contractCmpny.channel.channelId.eq(channelRsMast.channel.channelId))).fetchJoin()
                .where(filterByRevnSettleFndSearchDTO(searchDTO))
                .groupBy(channelRevnMastCmp.contractCmpny.channel.channelId
                        , channelRevnMastCmp.contractCmpny.channel.channelNm
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyId
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyNm
                        , channelRevnMastCmp.calYm
                        , channelRsMast.totAmt
                        , channelRevnMastCmp.calAmt
                        , channelRevnMastCmp.contractCmpny.rsRate)
                .orderBy(channelRevnMastCmp.contractCmpny.channel.channelNm.asc()
                        , channelRevnMastCmp.calYm.desc()
                )
                .fetch()
                .stream()
                .map(tuple -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("channelId", tuple.get(channelRevnMastCmp.contractCmpny.channel.channelId));
                    result.put("channelNm", tuple.get(channelRevnMastCmp.contractCmpny.channel.channelNm));
                    result.put("cmpnyId", tuple.get(channelRevnMastCmp.contractCmpny.cmpny.cmpnyId));
                    result.put("cmpnyNm", tuple.get(channelRevnMastCmp.contractCmpny.cmpny.cmpnyNm));
                    result.put("calYm", tuple.get(channelRevnMastCmp.calYm));
                    result.put("totAmt", tuple.get(channelRsMast.totAmt));
                    result.put("calAmt", tuple.get(channelRevnMastCmp.calAmt));
                    result.put("rsRate", tuple.get(channelRevnMastCmp.contractCmpny.rsRate));

                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> findRevnSettleSumBySearchDto(RevnFndSearchDTO searchDTO) {
        return query.select(channelRevnMastCmp.contractCmpny.channel.channelId
                        , channelRevnMastCmp.contractCmpny.channel.channelNm
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyId
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyNm
                        , channelRsMast.totAmt.sum().as("totAmt")
                        , channelRevnMastCmp.calAmt.sum().as("calAmt")
                )
                .from(channelRevnMastCmp)
                .innerJoin(channelRsMast).on(channelRevnMastCmp.calYm.eq(channelRsMast.calYm)
                        .and(channelRevnMastCmp.contractCmpny.channel.channelId.eq(channelRsMast.channel.channelId))).fetchJoin()
                .where(filterByRevnSettleFndSearchDTO(searchDTO))
                .groupBy(channelRevnMastCmp.contractCmpny.channel.channelId
                        , channelRevnMastCmp.contractCmpny.channel.channelNm
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyId
                        , channelRevnMastCmp.contractCmpny.cmpny.cmpnyNm)
                .orderBy(channelRevnMastCmp.contractCmpny.channel.channelNm.asc()
                )
                .fetch()
                .stream()
                .map(tuple -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("searchYm", searchDTO.getSchCalYmFrom() + "~" + searchDTO.getSchCalYmTo());
                    result.put("channelId", tuple.get(channelRevnMastCmp.contractCmpny.channel.channelId));
                    result.put("channelNm", tuple.get(channelRevnMastCmp.contractCmpny.channel.channelNm));
                    result.put("cmpnyId", tuple.get(channelRevnMastCmp.contractCmpny.cmpny.cmpnyId));
                    result.put("cmpnyNm", tuple.get(channelRevnMastCmp.contractCmpny.cmpny.cmpnyNm));
//                    result.put("totAmt", tuple.get(channelRsMast.totAmt));
//                    result.put("calAmt", tuple.get(channelRevnMastCmp.calAmt));
                    result.put("totAmt", tuple.get(4, Long.class));
                    result.put("calAmt", tuple.get(5, Long.class));

                    return result;
                })
                .collect(Collectors.toList());
    }

    private BooleanBuilder filterByRevnSettleFndSearchDTO(RevnFndSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!(StringUtils.isEmpty(searchDTO.getSchCalYmFrom()) || StringUtils.isEmpty(searchDTO.getSchCalYmTo())))
            builder.and(channelRevnMastCmp.calYm.between(searchDTO.getSchCalYmFrom(), searchDTO.getSchCalYmTo()));
        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channelRevnMastCmp.contractCmpny.channel.channelNm.like(searchDTO.getSchChannelNm() + "%"));

        return builder;
    }

    private BooleanBuilder dynamicFilter(ChannelRevnMastCmpSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channel.channelNm.like(searchDTO.getSchChannelNm()));
        if (!StringUtils.isEmpty(searchDTO.getSchCalYm()))
            builder.and(channelRevnMastCmp.calYm.eq(searchDTO.getSchCalYm()));


        return builder;
    }
}
