package com.example.revenueshare.biz.mng.base.domain.repository;

import com.example.revenueshare.biz.mng.base.domain.Creator;
import com.example.revenueshare.biz.mng.base.model.CreatorSearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.biz.mng.base.domain.QCreator.creator;

@RequiredArgsConstructor
public class CreatorRepositoryImpl implements CreatorRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Creator> findFetchAllByDto(CreatorSearchDTO searchDTO) {
        return query.selectFrom(creator)
                .where(dynamicFilter(searchDTO))
                .fetch();
    }

    private BooleanBuilder dynamicFilter(CreatorSearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchCreatorNm()))
            builder.and(creator.creatorNm.like(searchDTO.getSchCreatorNm()));

        return builder;
    }
}
