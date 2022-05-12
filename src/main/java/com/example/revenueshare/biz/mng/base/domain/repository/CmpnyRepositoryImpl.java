package com.example.revenueshare.biz.mng.base.domain.repository;

import com.example.revenueshare.biz.mng.base.domain.Cmpny;
import com.example.revenueshare.biz.mng.base.model.CmpnySearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.biz.mng.base.domain.QCmpny.cmpny;

@RequiredArgsConstructor
public class CmpnyRepositoryImpl implements CmpnyRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Cmpny> findAllByDto(CmpnySearchDTO searchDTO) {
        return query.selectFrom(cmpny)
                .where(dynamicFilter(searchDTO))
                .fetch();
    }

    private BooleanBuilder dynamicFilter(CmpnySearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(searchDTO.getSchCmpnyNm()))
            builder.and(cmpny.cmpnyNm.like(searchDTO.getSchCmpnyNm()));

        return builder;
    }
}
