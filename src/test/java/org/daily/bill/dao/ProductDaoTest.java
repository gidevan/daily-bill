package org.daily.bill.dao;

import org.daily.bill.domain.Bill;
import org.daily.bill.domain.BillItem;
import org.daily.bill.domain.Product;
import org.daily.bill.domain.Shop;
import org.daily.bill.utils.TestEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vano on 31.8.16.
 */
public class ProductDaoTest extends AbstractDaoTest<Long, Product, ProductDao> {

    private static final String DESCRIPTION_PREFIX = "Description: ";
    private static final String TEST_NAME = "TestProduct";
    private static final String[] PRODUCT_NAMES = {"Product1", "Product2", "Product3", "Product6", "Product5", "Product4"};
    private static final String UPDATED_NAME = "UpdatedProductName";
    private static final String SHOP_NAME = "TestShop";
    private static final String INACTIVE_PRODUCT_NAME = "InactiveProduct";
    private static final double AMOUNT = 1;
    private static final BigDecimal OLD_PRICE = BigDecimal.valueOf(12.4);
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(15.2);
    private static final double DELTA = 0.0001;
    private List<Product> testProducts = new ArrayList<>();
    private List<Shop> testShops = new ArrayList<>();
    private List<Bill> testBills = new ArrayList<>();
    private List<BillItem> testBillItems = new ArrayList<>();

    @Autowired
    private BillDao billDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private BillItemDao billItemDao;


    @BeforeClass
    public void before() throws Exception {
        for(String testName : PRODUCT_NAMES) {
            Product product = createEntity();
            product.setName(testName);
            product.setDescription(DESCRIPTION_PREFIX + testName);
            dao.create(product);
            testProducts.add(product);
        }
        createShop();
        addBill(new Date(), testProducts.get(DEFAULT_INDEX).getId(), OLD_PRICE);
        Thread.sleep(500);
        addBill(new Date(), testProducts.get(DEFAULT_INDEX).getId(), NEW_PRICE);

        Thread.sleep(500);
        addBill(new Date(), testProducts.get(DEFAULT_INDEX + 1).getId(), OLD_PRICE);
    }

    @AfterClass
    public void after() {
        for(BillItem bi : testBillItems) {
            billItemDao.delete(bi.getId());
            Assert.assertNull(billItemDao.findById(bi.getId()));
        }

        for(Bill bill : testBills) {
            billDao.delete(bill.getId());
            Assert.assertNull(billDao.findById(bill.getId()));
        }

        for(Product product : testProducts) {
            dao.delete(product.getId());
            Assert.assertNull(dao.findById(product.getId()));
        }
        for(Shop shop : testShops) {
            shopDao.delete(shop.getId());
            Assert.assertNull(dao.findById(shop.getId()));
        }
    }

    @Test
    @Override
    public void testFindAll() {
        List<Product> products = dao.findAll();
        Assert.assertEquals(products.size(), PRODUCT_NAMES.length);
        for(Product product : products) {
            Assert.assertTrue(Arrays.asList(PRODUCT_NAMES).contains(product.getName()));
            Assert.assertTrue(product.getDescription().startsWith(DESCRIPTION_PREFIX));
            Assert.assertTrue(product.getActive());
        }
    }

    @Test
    public void testFindProducts() {
        List<Product> products = dao.findProducts(true);
        Assert.assertFalse(products.isEmpty());
        Product pr = null;
        for(Product product : products) {
            if(pr != null) {
                Assert.assertTrue(pr.getName().compareTo(product.getName()) <= 0 );
                Assert.assertTrue(pr.getActive());
            }
            pr = product;
        }
    }

    @Test
    public void testFindInactiveProduct() {
        Product inactiveProduct = TestEntityFactory.createProduct(INACTIVE_PRODUCT_NAME, INACTIVE_PRODUCT_NAME);
        inactiveProduct.setActive(false);
        dao.create(inactiveProduct);

        Product storedInactiveProduct = dao.findById(inactiveProduct.getId());
        Assert.assertNotNull(storedInactiveProduct);

        List<Product> allProducts = dao.findProducts(false);

        List<Product> activeProducts = dao.findProducts(true);

        Assert.assertFalse(allProducts.isEmpty());
        Assert.assertFalse(activeProducts.isEmpty());

        Assert.assertTrue(allProducts.size() > activeProducts.size());

        List<Product> inactive = allProducts.stream().filter(pr -> !pr.getActive()).collect(Collectors.toList());
        Assert.assertTrue(inactive.size() == 1);
        Assert.assertFalse(inactive.get(DEFAULT_INDEX).getActive());

        List<Product> inactive1 = activeProducts.stream().filter(pr -> !pr.getActive()).collect(Collectors.toList());
        Assert.assertTrue(inactive1.isEmpty());

        dao.delete(storedInactiveProduct.getId());
        Assert.assertNull(dao.findById(storedInactiveProduct.getId()));

    }

    @Test
    public void testFindLastPrice() {
        BigDecimal lastPrice = dao.findLastPrice(testShops.get(DEFAULT_INDEX).getId(), testProducts.get(DEFAULT_INDEX).getId());
        Assert.assertEquals(lastPrice.doubleValue(), NEW_PRICE.doubleValue(), DELTA);
    }

    @Test
    public void testFindLastPriceNull() {
        BigDecimal lastPrice = dao.findLastPrice(testShops.get(DEFAULT_INDEX).getId(), testProducts.get(DEFAULT_INDEX + 2).getId());
        Assert.assertNull(lastPrice);
    }

    @Test
    public void testFindLastPriceOneProduct() {
        BigDecimal lastPrice = dao.findLastPrice(testShops.get(DEFAULT_INDEX).getId(), testProducts.get(DEFAULT_INDEX + 1).getId());
        Assert.assertEquals(lastPrice.doubleValue(), OLD_PRICE.doubleValue(), DELTA);
    }

    @Test
    public void testSetActiveFlag() {
        Long id  = testProducts.get(DEFAULT_INDEX).getId();
        Product pr = dao.findById(id);
        Assert.assertTrue(pr.getActive());
        pr.setActive(Boolean.FALSE);
        dao.update(pr);

        Product updated = dao.findById(id);
        Assert.assertFalse(updated.getActive());

        updated.setActive(Boolean.TRUE);
        dao.update(updated);

        Product product = dao.findById(id);
        Assert.assertTrue(product.getActive());
    }

    //@TODO: implement
    @Override
    protected void assertEntity(Product entity) {

    }

    @Override
    protected void assertUpdatedEntity(Product expected, Product updated) {
        Assert.assertEquals(updated.getName(), expected.getName());
        Assert.assertEquals(updated.getDescription(), expected.getDescription());
        Assert.assertNotNull(updated.getUpdated());
    }

    @Autowired
    @Override
    protected void setDao(ProductDao dao) {
        super.dao = dao;
    }

    @Override
    protected Product createEntity() {
        String name = TEST_NAME;
        return TestEntityFactory.createProduct(name, DESCRIPTION_PREFIX + name);
    }

    @Override
    protected void updateEntity(Product entity) {
        entity.setName(UPDATED_NAME);
        entity.setDescription(DESCRIPTION_PREFIX + UPDATED_NAME);
        entity.setUpdated(new Date());
    }

    private void addBill(Date date, Long productId, BigDecimal productPrice) {
        Bill bill = TestEntityFactory.createBill(testShops.get(DEFAULT_INDEX).getId(), date);
        billDao.create(bill);
        testBills.add(bill);

        BillItem bi = TestEntityFactory.createBillItem(bill.getId(), productId, productPrice, AMOUNT);
        billItemDao.create(bi);
        testBillItems.add(bi);
    }

    private void createShop() {
        Shop shop = TestEntityFactory.createShop(SHOP_NAME);
        shopDao.create(shop);
        testShops.add(shop);

    }
}
