package com.example.revenueshare.ctgy.revn.domain.repository;

import com.example.revenueshare.ctgy.revn.domain.ChannelRsMast;
import com.example.revenueshare.ctgy.revn.model.ChannelRsMastSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.ctgy.base.domain.QChannel.channel;
import static com.example.revenueshare.ctgy.base.domain.QChannelRsMast.channelRsMast;

@RequiredArgsConstructor
public class ChannelRsMastRepositoryImpl implements ChannelRsMastRepositoryCustom {
    
    private final JPAQueryFactory query;

    @Override
    public List<ChannelRsMast> findAllByDto(ChannelRsMastSearchDTO searchDTO) {
        return query.selectFrom(channelRsMast)
                .innerJoin(channelRsMast.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
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
