package com.example.revenueshare.biz.mng.cntrt.domain.repository;

import com.example.revenueshare.biz.mng.cntrt.domain.ContractCreator;
import com.example.revenueshare.biz.mng.cntrt.model.ContractCreatorSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.biz.mng.base.domain.QChannel.channel;
import static com.example.revenueshare.biz.mng.base.domain.QCreator.creator;
import static com.example.revenueshare.biz.mng.cntrt.domain.QContractCreator.contractCreator;

@RequiredArgsConstructor
public class ContractCreatorRepositoryImpl implements ContractCreatorRepositoryCustom {
    
    private final JPAQueryFactory query;

    @Override
    public List<ContractCreator> findFetchAllByDto(ContractCreatorSearchDTO searchDTO) {
        return query.selectFrom(contractCreator)
                .innerJoin(contractCreator.creator, creator).fetchJoin()
                .innerJoin(contractCreator.channel, channel).fetchJoin()
                .where(dynamicFilter(searchDTO))
                .fetch();
    }

    private BooleanBuilder dynamicFilter(ContractCreatorSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchCreatorNm()))
            builder.and(creator.creatorNm.like(searchDTO.getSchCreatorNm()));
        if (!StringUtils.isEmpty(searchDTO.getSchChannelNm()))
            builder.and(channel.channelNm.like(searchDTO.getSchChannelNm()));


        return builder;
    }
}
