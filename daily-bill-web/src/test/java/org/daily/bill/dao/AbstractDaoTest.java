package org.daily.bill.dao;

import org.daily.bill.config.Config;
import org.daily.bill.config.DataConfig;
import org.daily.bill.domain.Identifiable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by vano on 19.8.16.
 */
@ContextConfiguration(classes = {Config.class, DataConfig.class})
@TestPropertySource(locations={"classpath:application-test.properties"})
@Transactional(readOnly = true)
public abstract class AbstractDaoTest<ID, T extends Identifiable, D extends CrudDao> extends AbstractTestNGSpringContextTests {
    protected static final int DEFAULT_INDEX = 0;

    protected D dao;

    @Test
    public void testInsertEntity() {

        T entity =  insertEntity();
        testDeleteEntity((ID)entity.getId());
    }

    @Test
    public void testFindById() {
        T entity = insertEntity();
        Identifiable stored = dao.findById(entity.getId());
        Assert.assertNotNull(stored);
        testDeleteEntity((ID)stored.getId());
    }

    public abstract void testFindAll();

    @Test
    public void testDeleteEntity() {
        T entity = insertEntity();
        Identifiable stored = dao.findById(entity.getId());
        Assert.assertNotNull(stored);
        testDeleteEntity((ID)stored.getId());

    }

    protected void testDeleteEntity(ID id) {
        Assert.assertNotNull(id);
        dao.delete(id);
        Identifiable stored = dao.findById(id);
        Assert.assertNull(stored);
    }

    protected T insertEntity() {
        T entity = createEntity();
        dao.create(entity);
        Assert.assertNotNull(entity.getId());
        T stored = (T)dao.findById(entity.getId());
        Assert.assertNotNull(stored);
        assertEntity(stored);
        return stored;
    }

    protected abstract void assertEntity(T entity);

    protected abstract void assertUpdatedEntity(T expected, T updated);

    protected abstract void  setDao(D dao);

    protected abstract T createEntity();
    protected abstract void updateEntity(T entity);
}
