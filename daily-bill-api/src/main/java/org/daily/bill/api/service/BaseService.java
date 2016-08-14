package org.daily.bill.api.service;

import java.util.List;

/**
 * Created by vano on 14.8.16.
 */
public interface BaseService<T, ID> {

    void create(T entity);

    int update(T entity);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
