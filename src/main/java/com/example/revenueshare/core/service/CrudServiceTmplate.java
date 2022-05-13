package com.example.revenueshare.core.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.model.ResponseVO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CrudServiceTmplate<T, SDT, DT, ID> implements CrudService<T, SDT, DT, ID>{


    protected ResponseVO validation(DT p){

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DT>> list = validator.validate(p);
        if (list.size() > 0) {
            return ResponseVO.errBuilder()
                    .errCd(ErrCd.ERR401)
                    .errMsg("유효하지 않은 요청값 입니다.")
                    .resultInfo(list.stream()
                            .map(row -> String.format("%s [ %s ]", row.getMessageTemplate(), row.getPropertyPath()))
                            .collect(Collectors.toList()))
                    .build();
        }

        return ResponseVO.okBuilder().build();
    }

    public void removeById(ID id) throws Exception {
        throw new Exception("no process");
    }
    public void removeAllById(List<ID> ids) throws Exception {
        throw new Exception("no process");
    }
}
