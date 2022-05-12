package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.ctgy.revn.model.ChannelRevnMastCrtSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.ctgy.base.domain.QChannel.channel;
import static com.example.revenueshare.ctgy.base.domain.QChannelRevnMastCrt.channelRevnMastCrt;
import static com.example.revenueshare.ctgy.base.domain.QContractCreator.contractCreator;

@RequiredArgsConstructor
public class ChannelRevnMastCrtRepositoryImpl implements ChannelRevnMastCrtRepositoryCustom {
    
    private final JPAQueryFactory query;

    @Override
    public List<ChannelRevnMastCrt> findAllByDto(ChannelRevnMastCrtSearchDTO searchDTO) {
        return query.selectFrom(channelRevnMastCrt)
                .innerJoin(channelRevnMastCrt.contractCreator, contractCreator).fetchJoin()
                .innerJoin(contractCreator.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
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
