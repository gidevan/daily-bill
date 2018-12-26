package org.daily.bill.dao;

import org.daily.bill.domain.*;
import org.daily.bill.domain.Currency;
import org.daily.bill.utils.TestEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by vano on 3.9.16.
 */
public class BillDaoTest extends AbstractDaoTest<Long, Bill, BillDao> {

    private static final String[] SHOP_NAMES = {"ShopName1", "ShopName2"};
    private static final String[] PRODUCT_NAMES = {"Product1", "Product2"};
    private static final BigDecimal PRICES[] = {BigDecimal.valueOf(12.4), BigDecimal.valueOf(23.5)};
    private static final double AMOUNTS[] = {1, 3};
    private static final Object[][] PRODUCT_INFO = {{PRODUCT_NAMES[0], PRICES[0], AMOUNTS[0]},
            {PRODUCT_NAMES[1], PRICES[1], AMOUNTS[1]}
    };
    private static final String WRONG_NAME = "wrong name";
    private static final long UPDATE_CURRENCY_ID = 2;

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BillItemDao billItemDao;
    @Autowired
    private CurrencyDao currencyDao;
    private Map<String, Shop> shops = new HashMap<>();
    private Map<String, Product> products = new HashMap<>();

    @BeforeClass
    public void beforeClass() {
        createProducts();
        createShops();
    }
    @AfterClass
    public void afterClass() {
        deleteShops();
        deleteProducts();
    }

    @Test
    @Override
    public void testFindAll() {
        Bill bill1 = TestEntityFactory.createBill(shops.get(SHOP_NAMES[0]).getId(), new Date());
        Bill bill2 = TestEntityFactory.createBill(shops.get(SHOP_NAMES[1]).getId(), new Date());
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
        Assert.assertEquals(expected.getCurrency().getId(), updated.getCurrency().getId());
    }

    @Override
    protected void assertEntity(Bill entity) {
        Assert.assertNotNull(entity.getId());
        Assert.assertNotNull(entity.getCreated());
        Assert.assertNotNull(entity.getShop());
        Assert.assertNotNull(entity.getShop().getId());
        Assert.assertNotNull(entity.getCurrency().getId());
    }

    @Autowired
    @Override
    protected void setDao(BillDao dao) {
        super.dao = dao;
    }

    @Override
    protected Bill createEntity() {
        return TestEntityFactory.createBill(shops.get(SHOP_NAMES[0]).getId(), new Date());
    }

    @Override
    protected void updateEntity(Bill entity) {
        Shop shop = new Shop();
        shop.setId(shops.get(SHOP_NAMES[1]).getId());
        entity.setDate(new Date());
        Currency currency = new Currency();
        currency.setId(UPDATE_CURRENCY_ID);
        entity.setCurrency(currency);
    }

    @Test
    public void testGetBills() {
        Bill bill = TestEntityFactory.createBill(shops.get(SHOP_NAMES[0]).getId(), new Date());
        dao.create(bill);
        Long billId = bill.getId();
        Assert.assertNotNull(billId);
        createBillItems(billId);
        List<Bill> bills = dao.getBills(new BillListParams());
        Date date = new Date();
        Assert.assertFalse(bills.isEmpty());
        for(Bill stored: bills) {
            boolean dateCondition = date.compareTo(stored.getDate()) >= 0;
            Assert.assertTrue(dateCondition);
            Assert.assertNotNull(stored.getId());
            Assert.assertNotNull(stored.getShop().getName());
            Assert.assertNotNull(stored.getCurrency().getId());
            Assert.assertNotNull(stored.getCurrency().getCode());
            Assert.assertNotNull(stored.getCurrency().getName());
            date = stored.getDate();
        }
        deleteBillItems();
        dao.delete(bill.getId());
    }

    @Test
    public void testGetBillDetails() {
        Bill bill = TestEntityFactory.createBill(shops.get(SHOP_NAMES[0]).getId(), new Date());
        dao.create(bill);
        Long billId = bill.getId();
        Assert.assertNotNull(billId);
        createBillItems(billId);

        List<BillDetails> billDetails = dao.getBillDetails(billId);
        Assert.assertFalse(billDetails.isEmpty());
        for(BillDetails details : billDetails) {
            Assert.assertEquals(details.getBillId(), billId);
            Assert.assertEquals(details.getShopName(), SHOP_NAMES[0]);
            Assert.assertNotNull(details.getCurrencyId());
            Assert.assertNotNull(details.getCurrencyCode());
            Assert.assertNotNull(details.getCurrencyName());
        }
        deleteBillItems();
        dao.delete(billId);
        Assert.assertNull(dao.findById(billId));
    }

    @Test
    public void testGetStatisticsByProduct() {
        Bill bill = TestEntityFactory.createBill(shops.get(SHOP_NAMES[0]).getId(), new Date());
        dao.create(bill);
        Long billId = bill.getId();
        Assert.assertNotNull(billId);
        createBillItems(billId);
        StatisticsParams statisticsParams = new StatisticsParams();
        statisticsParams.setProductNames(Arrays.asList(PRODUCT_NAMES[0], PRODUCT_NAMES[1]));
        List<StatisticDetails> details = dao.getStatisticByProduct(statisticsParams);
        Assert.assertFalse(details.isEmpty());
        deleteBillItems();
        dao.delete(billId);
        Assert.assertNull(dao.findById(billId));
    }

    @Test
    public void testGetStatisticsByProductWrongName() {
        Bill bill = TestEntityFactory.createBill(shops.get(SHOP_NAMES[0]).getId(), new Date());
        dao.create(bill);
        Long billId = bill.getId();
        Assert.assertNotNull(billId);
        createBillItems(billId);
        StatisticsParams statisticsParams = new StatisticsParams();
        statisticsParams.setProductNames(Arrays.asList(WRONG_NAME));
        List<StatisticDetails> details = dao.getStatisticByProduct(statisticsParams);
        Assert.assertTrue(details.isEmpty());
        deleteBillItems();
        dao.delete(billId);
        Assert.assertNull(dao.findById(billId));
    }

    private void createShops() {
        shops.clear();
        for(String shopName : SHOP_NAMES) {
            Shop shop = TestEntityFactory.createShop(shopName);
            shopDao.create(shop);
        }

        List<Shop> stored = shopDao.findAll();
        for(Shop shop : stored) {
            Assert.assertTrue(Arrays.asList(SHOP_NAMES).contains(shop.getName()));
            shops.put(shop.getName(), shop);
        }
    }

    private void createProducts() {
        products.clear();
        for(String name: PRODUCT_NAMES) {
            Product product = TestEntityFactory.createProduct(name, name);
            productDao.create(product);

        }
        List<Product> stored = productDao.findAll();
        for(Product product : stored) {
            Assert.assertTrue(Arrays.asList(PRODUCT_NAMES).contains(product.getName()));
            products.put(product.getName(), product);
        }
    }

    private void deleteShops() {
        List<Shop> shops = shopDao.findAll();
        for(Shop shop : shops) {
            shopDao.delete(shop.getId());
        }
        Assert.assertTrue(shopDao.findAll().isEmpty());
    }

    private void deleteProducts() {
        List<Product> products = productDao.findAll();
        for(Product product : products) {
            productDao.delete(product.getId());
        }
        Assert.assertTrue(productDao.findAll().isEmpty());
    }

    private void createBillItems(Long billId) {
        for(Object[] productInfo : PRODUCT_INFO) {
            BillItem item = TestEntityFactory.createBillItem(billId,
                    products.get(productInfo[0]).getId(), (BigDecimal) productInfo[1],
                    (Double) productInfo[2]);
            billItemDao.create(item);
        }
    }

    private void deleteBillItems() {
        List<BillItem> billItems = billItemDao.findAll();
        for(BillItem item : billItems) {
            billItemDao.delete(item.getId());
        }
        Assert.assertTrue(billItemDao.findAll().isEmpty());
    }
}
