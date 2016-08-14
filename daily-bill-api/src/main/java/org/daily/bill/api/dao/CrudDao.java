package org.daily.bill.api.dao;

import java.util.List;

/**
 * Created by vano on 13.8.16.
 */
public interface CrudDao<T, ID> {

    void create(T entity);

    int update(T entity);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
