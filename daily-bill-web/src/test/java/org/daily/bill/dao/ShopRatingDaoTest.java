package org.daily.bill.dao;

import org.daily.bill.domain.Shop;
import org.daily.bill.domain.ShopRating;
import org.daily.bill.utils.TestEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopRatingDaoTest extends AbstractDaoTest<Long, ShopRating, ShopRatingDao> {

    private static final String[] SHOP_NAMES = {"ShopName1", "ShopName2"};
    private static final String INACTIVE_SHOP_NAME = "InactiveShopName1";
    private static final double RATING = 5.5;
    private static final double UPDATED_RATING = 6.5;

    private List<Shop> shops = new ArrayList<>();

    @Autowired
    private ShopDao shopDao;

    @BeforeClass
    public void beforeClass() {
        for(String shopName : SHOP_NAMES) {
            Shop shop = TestEntityFactory.createShop(shopName);
            shopDao.create(shop);
            Assert.assertNotNull(shop.getId());
            shops.add(shop);
        }

        Shop inactiveShop = TestEntityFactory.createShop(INACTIVE_SHOP_NAME);
        shopDao.create(inactiveShop);
        shops.add(inactiveShop);
    }

    @AfterClass
    public void afterClass() {
        List<ShopRating> ratings = dao.findAll();
        for(ShopRating rating : ratings) {
            dao.delete(rating.getId());
        }
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
        throw new IllegalStateException("Implement it!!");
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
