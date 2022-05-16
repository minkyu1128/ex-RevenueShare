package com.example.revenueshare.core.service;

import javax.transaction.Transactional;

public abstract class CrudValidServiceTmplate<T, SDT, DT, ID> extends CrudServiceTmplate<T, SDT, DT, ID>{


    abstract protected T addProc(DT p);
    abstract protected void modifyProc(DT p);
    abstract protected void validate(DT p, ValidateType type);

    @Override
    @Transactional
    public T add(DT p) {
        validate(p, ValidateType.C);
        return addProc(p);
    }

    @Override
    @Transactional
    public void modify(DT p) {
        validate(p, ValidateType.U);
        modifyProc(p);
    }
}
