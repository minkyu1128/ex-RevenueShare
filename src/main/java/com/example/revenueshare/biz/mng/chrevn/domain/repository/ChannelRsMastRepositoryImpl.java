package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRsMast;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRsMastSearchDTO;
import com.example.revenueshare.biz.revnsett.model.RevnFndSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.revenueshare.biz.mng.base.domain.QChannel.channel;
import static com.example.revenueshare.biz.mng.chrevn.domain.QChannelRevnMastCrt.channelRevnMastCrt;
import static com.example.revenueshare.biz.mng.chrevn.domain.QChannelRsMast.channelRsMast;
import static com.example.revenueshare.biz.mng.cntrt.domain.QContractCreator.contractCreator;

@RequiredArgsConstructor
public class ChannelRsMastRepositoryImpl implements ChannelRsMastRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<ChannelRsMast> findFetchAllByDto(ChannelRsMastSearchDTO searchDTO) {
        return query.selectFrom(channelRsMast)
                .innerJoin(channelRsMast.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
    }

    @Override
    public List<Map<String, Object>> findRevnSettleBySearchDto(RevnFndSearchDTO searchDTO) {
        return query.select(channelRsMast.channel.channelId
                        , channelRsMast.calYm
                        , channelRsMast.channelAmt
                        , channelRevnMastCrt.contractCreator.creator.creatorId
                        , channelRevnMastCrt.contractCreator.creator.creatorNm
                        , channelRevnMastCrt.contractCreator.rsRate
                        , channelRevnMastCrt.calAmt
                )
                .from(channelRsMast)
                .innerJoin(channelRsMast.channel, channel)
                .innerJoin(contractCreator).on(channel.channelId.eq(contractCreator.channel.channelId))
                .innerJoin(channelRevnMastCrt).on(contractCreator.cntrCrtId.eq(channelRevnMastCrt.contractCreator.cntrCrtId).and(channelRevnMastCrt.calYm.eq(channelRsMast.calYm)))
                .where(filterByRevnSettleFndSearchDTO(searchDTO))
                .groupBy(channelRsMast.channel.channelId
                        , channelRsMast.calYm
                        , channelRsMast.channelAmt
                        , channelRevnMastCrt.contractCreator.creator.creatorId
                        , channelRevnMastCrt.contractCreator.creator.creatorNm
                        , channelRevnMastCrt.contractCreator.rsRate
                        , channelRevnMastCrt.calAmt)
                .fetch()
                .stream()
                .map(tuple -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("channelId", tuple.get(channelRsMast.channel.channelId));
                    result.put("calYm", tuple.get(channelRsMast.calYm));
                    result.put("channelAmt", tuple.get(channelRsMast.channelAmt));
                    result.put("creatorId", tuple.get(channelRevnMastCrt.contractCreator.creator.creatorId));
                    result.put("creatorNm", tuple.get(channelRevnMastCrt.contractCreator.creator.creatorNm));
                    result.put("rsRate", tuple.get(channelRevnMastCrt.contractCreator.rsRate));
                    result.put("calAmt", tuple.get(channelRevnMastCrt.calAmt));

                    return result;
                })
                .collect(Collectors.toList());
    }

    private BooleanBuilder filterByRevnSettleFndSearchDTO(RevnFndSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!(StringUtils.isEmpty(searchDTO.getSearchCalYmFrom()) || StringUtils.isEmpty(searchDTO.getSearchCalYmTo())))
            builder.and(channelRsMast.calYm.between(searchDTO.getSearchCalYmFrom(), searchDTO.getSearchCalYmTo()));

        return builder;
    }

    private BooleanBuilder dynamicFilter(ChannelRsMastSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channel.channelNm.like(searchDTO.getSchChannelNm()));
        if (!StringUtils.isEmpty(searchDTO.getSchCalYm()))
            builder.and(channelRsMast.calYm.eq(searchDTO.getSchCalYm()));


        return builder;
    }
}
