package org.daily.bill.dao;


import org.daily.bill.domain.Identifiable;

import java.util.List;

public abstract class AbstractCrudDao<T extends Identifiable, ID> extends AbstractDao implements CrudDao<T, ID> {

    @Override
    public int create(T entity) {
        return getSqlSession().insert(getNamespace() + ".insert", entity);
    }
    @Override
    public int update(T entity) {
        return getSqlSession().update(getNamespace() + ".update", entity);
    }
    @Override
    public void delete(ID id) {
        getSqlSession().delete(getNamespace() + ".delete", id);
    }
    @Override
    public T findById(ID id) {
        return getSqlSession().selectOne(getNamespace() + ".findById", id);
    }

    @Override
    public List<T> findAll() {
        return getSqlSession().selectList(getNamespace() + ".findAll");
    }

    protected abstract String getNamespace();
}
