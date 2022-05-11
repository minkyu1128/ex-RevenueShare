package com.example.revenueshare.core.service;

import java.util.List;

public interface CrudService<T, P, ID> {

    T findAllBy(P p);
    T findById(ID id);
    void add(P p);
    void modify(P p);
    void remove(P p) throws Exception;
    void removeAllById(List<ID> ids) throws Exception;
}
