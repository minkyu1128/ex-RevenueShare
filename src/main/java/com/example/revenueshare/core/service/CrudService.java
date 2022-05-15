package com.example.revenueshare.core.service;

import java.util.List;

public interface CrudService<T, SDT, DT, ID> {

    T findAllBy(SDT p) throws Exception;
    T findById(ID id) throws Exception;
    void add(DT p);
    void modify(DT p) throws Exception;
    void removeById(ID id) throws Exception;
    void removeAllById(List<ID> ids) throws Exception;
}
