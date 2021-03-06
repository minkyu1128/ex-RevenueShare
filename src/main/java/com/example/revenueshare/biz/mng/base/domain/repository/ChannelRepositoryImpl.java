package com.example.revenueshare.biz.mng.base.domain.repository;

import com.example.revenueshare.biz.mng.base.domain.Channel;
import com.example.revenueshare.biz.mng.base.model.ChannelSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.biz.mng.base.domain.QChannel.channel;

@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Channel> findFetchAllByDto(ChannelSearchDTO searchDTO) {
        return query.selectFrom(channel)
                .where(channel.useYn.eq("Y")
                        .and(dynamicFilter(searchDTO))
                )
                .fetch();
    }

    private BooleanBuilder dynamicFilter(ChannelSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channel.channelNm.like(searchDTO.getSchChannelNm()));

        return builder;
    }
}
