package com.example.revenueshare.core.service;

import com.example.revenueshare.core.exception.ErrCd;
import com.example.revenueshare.core.model.ResponseVO;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CrudValidServiceTmplate<T, SDT, DT, ID> extends CrudServiceTmplate<T, SDT, DT, ID>{


    abstract protected void addProc(DT p);
    abstract protected void modifyProc(DT p);
    abstract protected void validate(DT p, ValidateType type);

    @Override
    @Transactional
    public void add(DT p){
        validate(p, ValidateType.C);
        addProc(p);
    }

    @Override
    @Transactional
    public void modify(DT p){
        validate(p, ValidateType.U);
        modifyProc(p);
    }
}
