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
import java.util.stream.Collectors;

/**
 * Created by vano on 18.8.16.
 */
@Test
public class ShopDaoTest extends AbstractDaoTest<Long, Shop, ShopDao> {
    private static final String TEST_NAME = "TestShop";
    private static final String TEST_NAMES[] = {"TestShop1", "TestShop2", "TestShop3", "TestShop5", "TestShop4"};
    private static final String UPDATED_TEST_NAME = "UpdatedTestShop";
    private static final String INACTIVE_SHOP_NAME = "InactiveShop";
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
        List<Shop> shops = dao.findShops(true);
        Assert.assertFalse(shops.isEmpty());
        Shop sh = null;
        for(Shop shop : shops) {
            if(sh != null) {
                Assert.assertTrue(sh.getName().compareTo(shop.getName()) <=0);
                Assert.assertTrue(sh.getActive());
            }
            sh = shop;
        }
    }

    @Test
    public void testFindInactiveShops() {
        Shop inactiveShop = TestEntityFactory.createShop(INACTIVE_SHOP_NAME);
        inactiveShop.setActive(false);
        dao.create(inactiveShop);

        Shop storedInactiveShop = dao.findById(inactiveShop.getId());
        Assert.assertNotNull(storedInactiveShop);

        List<Shop> allShops = dao.findShops(false);

        List<Shop> activeShops = dao.findShops(true);

        Assert.assertFalse(allShops.isEmpty());
        Assert.assertFalse(activeShops.isEmpty());

        Assert.assertTrue(allShops.size() > activeShops.size());

        List<Shop> inactive = allShops.stream().filter(sh -> !sh.getActive()).collect(Collectors.toList());
        Assert.assertTrue(inactive.size() == 1);
        Assert.assertFalse(inactive.get(DEFAULT_INDEX).getActive());

        List<Shop> inactive1 = activeShops.stream().filter(sh -> !sh.getActive()).collect(Collectors.toList());
        Assert.assertTrue(inactive1.isEmpty());

        dao.delete(storedInactiveShop.getId());
        Assert.assertNull(dao.findById(storedInactiveShop.getId()));

    }

    @Test
    public void testSetActiveFlag() {
        Long id  = testShops.get(DEFAULT_INDEX).getId();
        Shop shop = dao.findById(id);
        Assert.assertTrue(shop.getActive());
        shop.setActive(Boolean.FALSE);
        dao.update(shop);

        Shop updated = dao.findById(id);
        Assert.assertFalse(updated.getActive());

        updated.setActive(Boolean.TRUE);
        dao.update(updated);
        Shop shop1 = dao.findById(id);
        Assert.assertTrue(shop1.getActive());
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
