package com.example.revenueshare.biz.mng.chrevn.domain.repository;

import com.example.revenueshare.biz.mng.chrevn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.chrevn.model.ChannelRevnMastCrtSearchDTO;
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
import static com.example.revenueshare.biz.mng.cntrt.domain.QContractCreator.contractCreator;

@RequiredArgsConstructor
public class ChannelRevnMastCrtRepositoryImpl implements ChannelRevnMastCrtRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<ChannelRevnMastCrt> findFetchAllByDto(ChannelRevnMastCrtSearchDTO searchDTO) {
        return query.selectFrom(channelRevnMastCrt)
                .innerJoin(channelRevnMastCrt.contractCreator, contractCreator).fetchJoin()
                .innerJoin(contractCreator.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
    }

    @Override
    public List<Map<String, Object>> findRevnSettleBySearchDto(RevnFndSearchDTO searchDTO) {
        return query.select(channelRevnMastCrt.contractCreator.channel.channelId
                        , channelRevnMastCrt.contractCreator.channel.channelNm
                        , channelRevnMastCrt.contractCreator.creator.creatorId
                        , channelRevnMastCrt.contractCreator.creator.creatorNm
                        , channelRevnMastCrt.calYm
                        , channelRevnMastCrt.calAmt
                )
                .from(channelRevnMastCrt)
                .where(filterByRevnSettleFndSearchDTO(searchDTO))
                .groupBy(channelRevnMastCrt.contractCreator.channel.channelId
                        , channelRevnMastCrt.contractCreator.channel.channelNm
                        , channelRevnMastCrt.calYm
                        , channelRevnMastCrt.calAmt)
                .orderBy(channelRevnMastCrt.contractCreator.channel.channelNm.asc()
                        , channelRevnMastCrt.calYm.asc()
                )
                .fetch()
                .stream()
                .map(tuple -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("channelId", tuple.get(channelRevnMastCrt.contractCreator.channel.channelId));
                    result.put("channelNm", tuple.get(channelRevnMastCrt.contractCreator.channel.channelNm));
                    result.put("creatorId", tuple.get(channelRevnMastCrt.contractCreator.creator.creatorId));
                    result.put("creatorNm", tuple.get(channelRevnMastCrt.contractCreator.creator.creatorNm));
                    result.put("calYm", tuple.get(channelRevnMastCrt.calYm));
                    result.put("calAmt", tuple.get(channelRevnMastCrt.calAmt));

                    return result;
                })
                .collect(Collectors.toList());
    }

    private BooleanBuilder filterByRevnSettleFndSearchDTO(RevnFndSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!(StringUtils.isEmpty(searchDTO.getSearchCalYmFrom()) || StringUtils.isEmpty(searchDTO.getSearchCalYmTo())))
            builder.and(channelRevnMastCrt.calYm.between(searchDTO.getSearchCalYmFrom(), searchDTO.getSearchCalYmTo()));

        return builder;
    }

    private BooleanBuilder dynamicFilter(ChannelRevnMastCrtSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channel.channelNm.like(searchDTO.getSchChannelNm()));
        if (!StringUtils.isEmpty(searchDTO.getSchCalYm()))
            builder.and(channelRevnMastCrt.calYm.eq(searchDTO.getSchCalYm()));


        return builder;
    }
}
