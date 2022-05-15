package com.example.revenueshare.core.service;

public abstract class OnlyCreateServiceTmplate<T, SDT, DT, ID> extends CrudValidServiceTmplate<T, SDT, DT, ID> {

    @Override
    public T findAllBy(SDT p) throws Exception {
        throw new RuntimeException("no process");
    }


    @Override
    public T findById(ID id) throws Exception {
        throw new RuntimeException("no process");
    }

    @Override
    public void modify(DT p) {
        throw new RuntimeException("no process");
    }

    @Override
    protected void modifyProc(DT p) {
        throw new RuntimeException("no process");
    }
}
