package org.daily.bill.dao;

import org.daily.bill.api.dao.ShopDao;
import org.daily.bill.domain.BillItem;
import org.daily.bill.domain.Shop;

import org.daily.bill.utils.TestEntityFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by vano on 18.8.16.
 */
@Test
public class ShopDaoTest extends AbstractDaoTest<Long, Shop, ShopDao> {
    private static final String TEST_NAME = "TestShop";
    private static final String TEST_NAMES[] = {"TestShop1", "TestShop2", "TestShop3", "TestShop5", "TestShop4"};
    private static final String UPDATED_TEST_NAME = "UpdatedTestShop";
    private List<Shop> testShops = new ArrayList<>();

    @BeforeClass
    public void before() {
        for(String testName : TEST_NAMES) {
            Shop shop = createEntity();
            shop.setName(testName);
            dao.create(shop);
            testShops.add(shop);
        }
    }

    @AfterClass
    public void afterTest() {
        for(Shop shop : testShops) {
            dao.delete(shop.getId());
            Assert.assertNull(dao.findById(shop.getId()));
        }
    }

    @Test
    @Override
    public void testFindAll() {

        List<Shop> shops = dao.findAll();
        Assert.assertEquals(shops.size(), TEST_NAMES.length);
        for(Shop shop : shops) {
            Assert.assertTrue(Arrays.asList(TEST_NAMES).contains(shop.getName()));
        }

    }

    @Test
    public void testFindShops() {
        List<Shop> shops = dao.findShops();
        Assert.assertFalse(shops.isEmpty());
        Shop sh = null;
        for(Shop shop : shops) {
            if(sh != null) {
                Assert.assertTrue(sh.getName().compareTo(shop.getName()) <=0);
            }
            sh = shop;
        }
    }

    //@TODO: implement
    @Override
    protected void assertEntity(Shop entity) {

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
        return TestEntityFactory.createShop(TEST_NAME);
    }

    @Override
    protected void updateEntity(Shop shop) {
        shop.setName(UPDATED_TEST_NAME);
        shop.setUpdated(new Date());
    }
}
