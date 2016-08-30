package org.daily.bill.api.dao;

import org.daily.bill.domain.Identifiable;

import java.util.List;

/**
 * Created by vano on 13.8.16.
 */
public interface CrudDao<T extends Identifiable, ID> {

    int create(T entity);

    int update(T entity);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
