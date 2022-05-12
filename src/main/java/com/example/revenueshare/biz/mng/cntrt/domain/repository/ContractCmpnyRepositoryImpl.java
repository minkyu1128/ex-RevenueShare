package com.example.revenueshare.biz.mng.cntrt.domain.repository;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCmpny;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCmpnySearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.biz.mng.base.domain.QChannel.channel;
import static com.example.revenueshare.biz.mng.base.domain.QCmpny.cmpny;
import static com.example.revenueshare.biz.mng.cntrt.domain.QContractCmpny.contractCmpny;

@RequiredArgsConstructor
public class ContractCmpnyRepositoryImpl implements ContractCmpnyRepositoryCustom {
    
    private final JPAQueryFactory query;

    @Override
    public List<ContractCmpny> findAllByDto(ContractCmpnySearchDTO searchDTO) {
        return query.selectFrom(contractCmpny)
                .innerJoin(contractCmpny.cmpny, cmpny).fetchJoin()
                .innerJoin(contractCmpny.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
    }

    private BooleanBuilder dynamicFilter(ContractCmpnySearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchCmpnyNm()))
            builder.and(cmpny.cmpnyNm.like(searchDTO.getSchCmpnyNm()));
        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channel.channelNm.like(searchDTO.getSchChannelNm()));


        return builder;
    }
}
