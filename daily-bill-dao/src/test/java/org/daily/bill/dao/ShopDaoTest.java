package org.daily.bill.dao;

import org.daily.bill.api.dao.ShopDao;
import org.daily.bill.domain.Shop;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by vano on 18.8.16.
 */
@Test
public class ShopDaoTest extends AbstractDaoTest<Long, Shop, ShopDao> {
    private static final String TEST_NAMES[] = {"TestShop1", "TestShop2", "TestShop3"};
    private static final String UPDATED_TEST_NAME = "UpdatedTestShop";

    @Test
    @Override
    public void testFindAll() {
        for(String testName : TEST_NAMES) {
            Shop shop = createEntity();
            shop.setName(testName);
            dao.create(shop);
        }

        List<Shop> shops = dao.findAll();
        Assert.assertEquals(shops.size(), TEST_NAMES.length);
        for(Shop shop : shops) {
            Assert.assertTrue(Arrays.asList(TEST_NAMES).contains(shop.getName()));
        }

        for(Shop shop : shops) {
            dao.delete(shop.getId());
            Assert.assertNull(dao.findById(shop.getId()));
        }
    }

    @Override
    protected void assertUpdatedEntity(Shop expected, Shop updated) {
        Assert.assertEquals(updated.getId(), expected.getId());
        Assert.assertEquals(updated.getName(), expected.getName(), UPDATED_TEST_NAME);
        Assert.assertNotNull(updated.getUpdated());
    }

    @Override
    @Autowired
    protected void setDao(ShopDao dao) {
        super.dao = dao;
    }

    @Override
    protected Shop createEntity() {
        Shop shop = new Shop();
        shop.setName(TEST_NAMES[0]);
        shop.setCreated(new Date());
        return shop;
    }

    @Override
    protected void updateEntity(Shop shop) {
        shop.setName(UPDATED_TEST_NAME);
        shop.setUpdated(new Date());
    }
}
