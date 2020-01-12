package org.daily.bill.dao;

import org.daily.bill.domain.Bill;
import org.daily.bill.domain.Shop;
import org.daily.bill.domain.ShopRating;
import org.daily.bill.domain.ShopView;
import org.daily.bill.utils.TestEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ShopRatingDaoTest extends AbstractDaoTest<Long, ShopRating, ShopRatingDao> {

    private static final String[] SHOP_NAMES = {"ShopName1", "ShopName2", "ShopName3", "ShopName4", "ShopName5"};
    private static final String INACTIVE_SHOP_NAME = "InactiveShopName1";
    private static final double RATING = 5.5;
    private static final double UPDATED_RATING = 6.53;
    private static final double MAX_RATING = 16.53;
    private static final double FAULT = 0.0001;

    private List<Shop> shops = new ArrayList<>();

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private BillDao billDao;

    @BeforeClass
    public void beforeClass() {
        for(String shopName : SHOP_NAMES) {
            Shop shop = TestEntityFactory.createShop(shopName, true);
            shopDao.create(shop);
            Assert.assertNotNull(shop.getId());
            shops.add(shop);
        }

        Shop inactiveShop = TestEntityFactory.createShop(INACTIVE_SHOP_NAME, false);
        shopDao.create(inactiveShop);
        shops.add(inactiveShop);
    }

    @AfterClass
    public void afterClass() {
        dao.deleteAll();
        Assert.assertFalse(shops.isEmpty());
        for(Shop shop : shops) {
            shopDao.delete(shop.getId());
        }
    }

    @Test
    @Override
    public void testFindAll() {
        ShopRating rating1 = createEntity(SHOP_NAMES[0], RATING);
        ShopRating rating2 = createEntity(SHOP_NAMES[1], UPDATED_RATING);
        ShopRating stored1 = insertEntity(rating1);
        ShopRating stored2 = insertEntity(rating2);

        List<ShopRating> shopRatings = dao.findAll();
        Assert.assertFalse(shopRatings.isEmpty());
        List<Long> expectedIds = Arrays.asList(stored1.getId(), stored2.getId());
        for(ShopRating rating : shopRatings) {
            Assert.assertTrue(expectedIds.contains(rating.getId()));
        }
    }

    @Test
    public void testFindShopsByRating() {
        ShopRating rating1 = createEntity(SHOP_NAMES[0], RATING);
        ShopRating rating2 = createEntity(SHOP_NAMES[1], UPDATED_RATING);
        ShopRating stored1 = insertEntity(rating1);
        ShopRating stored2 = insertEntity(rating2);

        ShopRating rating3 = createEntity(SHOP_NAMES[2], MAX_RATING);
        ShopRating stored3 = insertEntity(rating3);

        List<ShopView> shops = dao.findShopsByRating();

        Assert.assertFalse(shops.isEmpty());
        Double previousRating = MAX_RATING;
        boolean isNullRatingExists = false;
        for(ShopView sh : shops) {
            if (sh.getRating() != null) {
                Assert.assertTrue(Math.abs(previousRating - sh.getRating()) < FAULT
                        || previousRating >= sh.getRating());
            } else {
                isNullRatingExists = true;
            }
            Assert.assertFalse(INACTIVE_SHOP_NAME.equals(sh.getName()));
            previousRating = sh.getRating();

        }
        Assert.assertTrue(isNullRatingExists);
    }

    @Test
    public void testCalculateShopRating() {
        Shop shop1 = shops.stream().filter(sh -> SHOP_NAMES[0].equals(sh.getName())).findFirst().get();
        Shop shop2 = shops.stream().filter(sh -> SHOP_NAMES[1].equals(sh.getName())).findFirst().get();
        Bill bill11 = TestEntityFactory.createBill(shop1.getId(), new Date());
        Bill bill12 = TestEntityFactory.createBill(shop1.getId(), new Date());
        Bill bill21 = TestEntityFactory.createBill(shop2.getId(), new Date());
        billDao.create(bill11);
        billDao.create(bill12);
        billDao.create(bill21);

        dao.calculateShopRating();

        List<ShopRating> ratings = dao.findAll();

        Assert.assertFalse(ratings.isEmpty());
        List<Long> expectedShops = Arrays.asList(shop1.getId(), shop2.getId());
        for(ShopRating rating : ratings) {
            Assert.assertTrue(expectedShops.contains(rating.getShopId()));
            if (rating.getShopId().equals(shop1.getId())) {
                Assert.assertEquals(rating.getRating().intValue(), 2);
            } else {
                Assert.assertEquals(rating.getRating().intValue(), 1);
            }

        }
        billDao.delete(bill11.getId());
        billDao.delete(bill12.getId());
        billDao.delete(bill21.getId());
        dao.deleteAll();
    }

    @Test
    public void testCalculateShopRatingNoData() {
        Shop shop = shops.stream().filter(sh -> SHOP_NAMES[0].equals(sh.getName())).findFirst().get();
        LocalDateTime ldt = LocalDateTime.now().minusMonths(2);
        Date date = Date
                .from(ldt.atZone(ZoneId.systemDefault())
                        .toInstant());
        Bill bill = TestEntityFactory.createBill(shop.getId(), date);
        billDao.create(bill);

        dao.calculateShopRating();

        List<ShopRating> ratings = dao.findAll();

        Assert.assertTrue(ratings.isEmpty());
        billDao.delete(bill.getId());
        dao.deleteAll();
    }


    @Override
    protected void assertEntity(ShopRating entity) {
        Shop shop = shops.stream()
                .filter(sh -> SHOP_NAMES[0].equals(sh.getName()))
                .findFirst().get();
        Assert.assertEquals(shop.getId(), entity.getShopId());
        Assert.assertEquals(RATING, entity.getRating());
        Assert.assertNotNull(entity.getId());
        Assert.assertNotNull(entity.getCreated());
        Assert.assertNull(entity.getUpdated());
    }

    @Override
    protected void assertUpdatedEntity(ShopRating expected, ShopRating updated) {
        Shop shop = shops.stream().filter(sh -> SHOP_NAMES[1].equals(sh.getName())).findFirst().get();
        Assert.assertEquals(shop.getId(), updated.getShopId());
        Assert.assertEquals(UPDATED_RATING, updated.getRating());
        Assert.assertEquals(expected.getId(), updated.getId());
        Assert.assertEquals(expected.getCreated(), updated.getCreated());
        Assert.assertNotNull(updated.getUpdated());
    }

    @Autowired
    @Override
    protected void setDao(ShopRatingDao dao) {
        this.dao = dao;
    }

    @Override
    protected ShopRating createEntity() {
        return createEntity(SHOP_NAMES[0], RATING);
    }

    @Override
    protected void updateEntity(ShopRating entity) {
        Shop shop = shops.stream().filter(sh -> SHOP_NAMES[1].equals(sh.getName())).findFirst().get();
        entity.setRating(UPDATED_RATING);
        entity.setShopId(shop.getId());
    }

    private ShopRating createEntity(String shopName, Double rating) {
        Shop shop = shops.stream().filter(sh -> shopName.equals(sh.getName())).findFirst().get();
        return TestEntityFactory.createShopRating(shop.getId(), rating);
    }

}
