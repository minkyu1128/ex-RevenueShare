package com.example.revenueshare.core.service;

import javax.transaction.Transactional;

public abstract class CrudValidServiceTmplate<T, SDT, DT, ID> extends CrudServiceTmplate<T, SDT, DT, ID>{


    abstract protected void addProc(DT p);
    abstract protected void modifyProc(DT p);
    abstract protected void validate(DT p, ValidateType type);

    @Override
    @Transactional
    public void add(DT p) {
        validate(p, ValidateType.C);
        addProc(p);
    }

    @Override
    @Transactional
    public void modify(DT p) {
        validate(p, ValidateType.U);
        modifyProc(p);
    }
}
