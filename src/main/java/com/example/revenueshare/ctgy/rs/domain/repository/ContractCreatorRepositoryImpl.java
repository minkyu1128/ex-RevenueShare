package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.ContractCreator;
import com.example.revenueshare.ctgy.rs.model.ContractCreatorSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.ctgy.rs.domain.QChannel.channel;
import static com.example.revenueshare.ctgy.rs.domain.QCmpny.cmpny;
import static com.example.revenueshare.ctgy.rs.domain.QContractCreator.contractCreator;
import static com.example.revenueshare.ctgy.rs.domain.QCreator.creator;

@RequiredArgsConstructor
public class ContractCreatorRepositoryImpl implements ContractCreatorRepositoryCustom {
    
    private final JPAQueryFactory query;

    @Override
    public List<ContractCreator> findAllByDto(ContractCreatorSearchDTO searchDTO) {
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
