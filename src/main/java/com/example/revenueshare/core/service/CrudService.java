package com.example.revenueshare.core.service;

import java.util.List;

public interface CrudService<T, SDT, DT, ID> {

    T findAllBy(SDT p);
    T findById(ID id);
    void add(DT p);
    void modify(DT p);
    void removeById(ID id) throws Exception;
    void removeAllById(List<ID> ids) throws Exception;
}
