package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevn;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.biz.mng.base.domain.QChannel.channel;
import static com.example.revenueshare.biz.mng.revn.domain.QChannelRevn.channelRevn;

@RequiredArgsConstructor
public class ChannelRevnRepositoryImpl implements ChannelRevnRepositoryCustom {
    
    private final JPAQueryFactory query;

    @Override
    public List<ChannelRevn> findAllByDto(ChannelRevnSearchDTO searchDTO) {
        return query.selectFrom(channelRevn)
                .innerJoin(channelRevn.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO)
                        .and(channelRevn.delYn.eq("N")))
                .fetch();
    }

    private BooleanBuilder dynamicFilter(ChannelRevnSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channel.channelNm.like(searchDTO.getSchChannelNm()));
        if (!StringUtils.isEmpty(searchDTO.getSchRevnSeCd()))
            builder.and(channelRevn.revnSeCd.eq(searchDTO.getSchRevnSeCd()));


        return builder;
    }
}