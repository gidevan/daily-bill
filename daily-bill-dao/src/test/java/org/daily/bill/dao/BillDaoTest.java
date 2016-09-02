package org.daily.bill.dao;

import org.daily.bill.api.dao.BillDao;
import org.daily.bill.api.dao.ShopDao;
import org.daily.bill.domain.Bill;
import org.daily.bill.domain.Shop;
import org.daily.bill.utils.TestEntityFactory;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by vano on 3.9.16.
 */
public class BillDaoTest extends AbstractDaoTest<Long, Bill, BillDao> {

    private static final String[] SHOP_NAMES = {"ShopName1", "ShopName2"};

    @Autowired
    private ShopDao shopDao;
    private List<Shop> shops;

    @BeforeClass
    public void beforeClass() {
        for(String shopName : SHOP_NAMES) {
            Shop shop = TestEntityFactory.createShop(shopName);
            shopDao.create(shop);
        }

        shops = shopDao.findAll();
        for(Shop shop : shops) {
            Assert.assertTrue(Arrays.asList(SHOP_NAMES).contains(shop.getName()));
        }

    }
    @AfterClass
    public void afterClass() {
        List<Shop> shops = shopDao.findAll();
        for(Shop shop : shops) {
            shopDao.delete(shop.getId());
        }
        Assert.assertTrue(shopDao.findAll().isEmpty());
    }

    @Test
    @Override
    public void testFindAll() {
        Bill bill1 = TestEntityFactory.createBill(shops.get(DEFAULT_INDEX).getId(), new Date());
        Bill bill2 = TestEntityFactory.createBill(shops.get(DEFAULT_INDEX + 1).getId(), new Date());
        dao.create(bill1);
        dao.create(bill2);
        List<Bill> bills = dao.findAll();
        Assert.assertFalse(bills.isEmpty());
        Assert.assertEquals(bills.size(), SHOP_NAMES.length);
        for(Bill bill : bills) {
            dao.delete(bill.getId());
        }
        Assert.assertTrue(dao.findAll().isEmpty());

    }

    @Override
    protected void assertUpdatedEntity(Bill expected, Bill updated) {
        Assert.assertEquals(updated.getId(), expected.getId());
        Assert.assertEquals(updated.getDate(), expected.getDate());
        Assert.assertNotNull(updated.getUpdated());
    }

    @Autowired
    @Override
    protected void setDao(BillDao dao) {
        super.dao = dao;
    }

    @Override
    protected Bill createEntity() {
        return TestEntityFactory.createBill(shops.get(DEFAULT_INDEX).getId(), new Date());
    }

    @Override
    protected void updateEntity(Bill entity) {
        entity.setShopId(shops.get(DEFAULT_INDEX + 1).getId());
        entity.setDate(new Date());
    }
}
