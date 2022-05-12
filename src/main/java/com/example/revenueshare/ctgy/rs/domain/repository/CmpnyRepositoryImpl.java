package com.example.revenueshare.ctgy.rs.domain.repository;

import com.example.revenueshare.ctgy.rs.domain.Cmpny;
import com.example.revenueshare.ctgy.rs.model.CmpnySearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.revenueshare.ctgy.rs.domain.QCmpny.cmpny;

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
