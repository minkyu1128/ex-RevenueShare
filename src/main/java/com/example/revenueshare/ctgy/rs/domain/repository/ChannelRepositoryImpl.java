package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.Channel;
import com.example.revenueshare.ctgy.rs.model.ChannelDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.ctgy.rs.domain.QChannel.channel;

@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public List<Channel> findAllByDto(ChannelDTO dto) {
        return query.selectFrom(channel)
                .where(filter(dto))
                .fetch();
    }

    private BooleanBuilder filter(ChannelDTO dto){
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.isEmpty(dto.getSchChannelNm()))
            builder.and(channel.channelNm.like(dto.getSchChannelNm()));

        return builder;
    }
}
