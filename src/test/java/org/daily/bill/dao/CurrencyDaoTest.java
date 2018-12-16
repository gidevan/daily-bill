package org.daily.bill.dao;

import org.daily.bill.domain.Currency;
import org.daily.bill.utils.TestEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

/**
 * Created by vano on 21.8.18.
 */
public class CurrencyDaoTest extends AbstractDaoTest<Long, Currency, CurrencyDao> {

    private static final String TEST_CODE = "CURR_CODE1";
    private static final String TEST_UPDATED_CODE = "CURR_UPDATED_CODE1";
    private static final String TEST_NAME = "CURR_NAME2";
    private static final String TEST_UPDATED_NAME = "CURR_UPDATED_NAME2";
    @Override
    public void testFindAll() {

    }

    @Override
    protected void assertEntity(Currency entity) {
        Assert.assertNotNull(entity.getId());
        Assert.assertEquals(TEST_CODE, entity.getCode());
        Assert.assertEquals(TEST_NAME, entity.getName());
        Assert.assertFalse(entity.getDefaultCurrency());
    }

    @Override
    protected void assertUpdatedEntity(Currency expected, Currency updated) {
        Assert.assertEquals(expected.getId(), updated.getId());
        Assert.assertEquals(TEST_UPDATED_NAME, expected.getName(), updated.getName());
        Assert.assertEquals(TEST_UPDATED_CODE, expected.getCode(), updated.getCode());
        Assert.assertTrue(updated.getDefaultCurrency());
    }

    @Autowired
    @Override
    protected void setDao(CurrencyDao dao) {
        super.dao = dao;
    }

    @Override
    protected Currency createEntity() {
        return TestEntityFactory.createCurrency(TEST_CODE, TEST_NAME,Boolean.FALSE);
    }

    @Override
    protected void updateEntity(Currency entity) {
        entity.setCode(TEST_UPDATED_CODE);
        entity.setName(TEST_UPDATED_NAME);
        entity.setDefaultCurrency(Boolean.TRUE);
    }
}
