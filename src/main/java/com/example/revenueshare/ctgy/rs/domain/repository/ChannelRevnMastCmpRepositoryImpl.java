package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ChannelRevnMastCmp;
import com.example.revenueshare.ctgy.rs.model.ChannelRevnMastCmpSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.ctgy.rs.domain.QChannel.channel;
import static com.example.revenueshare.ctgy.rs.domain.QChannelRevnMastCmp.channelRevnMastCmp;
import static com.example.revenueshare.ctgy.rs.domain.QContractCmpny.contractCmpny;

@RequiredArgsConstructor
public class ChannelRevnMastCmpRepositoryImpl implements ChannelRevnMastCmpRepositoryCustom {
    
    private final JPAQueryFactory query;

    @Override
    public List<ChannelRevnMastCmp> findAllByDto(ChannelRevnMastCmpSearchDTO searchDTO) {
        return query.selectFrom(channelRevnMastCmp)
                .innerJoin(channelRevnMastCmp.contractCmpny, contractCmpny).fetchJoin()
                .innerJoin(contractCmpny.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
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
