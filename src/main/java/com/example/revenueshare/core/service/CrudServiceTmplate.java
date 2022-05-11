package com.example.revenueshare.core.service;

import java.util.List;

public abstract class CrudServiceTmplate<T, P, ID> implements CrudService<T, P, ID>{

    public void remove(P p) throws Exception {
        throw new Exception("no process");
    }
    public void removeAllById(List<ID> ids) throws Exception {
        throw new Exception("no process");
    }
}
