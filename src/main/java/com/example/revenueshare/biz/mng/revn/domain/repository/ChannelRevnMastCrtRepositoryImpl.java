package com.example.revenueshare.biz.mng.revn.domain.repository;

import com.example.revenueshare.biz.mng.revn.domain.ChannelRevnMastCrt;
import com.example.revenueshare.biz.mng.revn.model.ChannelRevnMastCrtSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.biz.mng.base.domain.QChannel.channel;
import static com.example.revenueshare.biz.mng.cntrt.domain.QContractCreator.contractCreator;
import static com.example.revenueshare.biz.mng.revn.domain.QChannelRevnMastCrt.channelRevnMastCrt;

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
