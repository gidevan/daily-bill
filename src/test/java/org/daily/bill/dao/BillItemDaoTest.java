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
import java.util.Date;
import java.util.List;

/**
 * Created by vano on 3.9.16.
 */
public class BillItemDaoTest extends AbstractDaoTest<Long, BillItem, BillItemDao> {

    private static final String[] PRODUCT_NAMES = {"ProductName1", "ProductName2"};
    private static final String[] SHOP_NAMES = {"ShopName1","ShopName2"};
    private static final BigDecimal TEST_PRICE = BigDecimal.ONE;
    private static final BigDecimal UPDATED_TEST_PRICE = BigDecimal.ZERO;
    private static final int TEST_COUNT = 2;
    private static final int UPDATED_TEST_COUNT = 5;

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private BillDao billDao;
    @Autowired
    private ProductDao productDao;

    private List<Product> products = new ArrayList<>();
    private Shop shop;
    private List<Bill> bills = new ArrayList<>();

    @BeforeClass
    public void beforeClass() {
        Product product1 = TestEntityFactory.createProduct(PRODUCT_NAMES[0], PRODUCT_NAMES[0]);
        productDao.create(product1);
        Assert.assertNotNull(product1.getId());
        Product product2 = TestEntityFactory.createProduct(PRODUCT_NAMES[1], PRODUCT_NAMES[1]);
        productDao.create(product2);
        products.add(product1);
        products.add(product2);


        shop = TestEntityFactory.createShop(SHOP_NAMES[0]);
        shopDao.create(shop);
        Assert.assertNotNull(shop.getId());

        Bill bill1 = TestEntityFactory.createBill(shop.getId(), new Date());
        Bill bill2 = TestEntityFactory.createBill(shop.getId(), new Date());
        billDao.create(bill1);
        billDao.create(bill2);
        bills.add(bill1);
        bills.add(bill2);
        Assert.assertNotNull(bill1.getId());
        Assert.assertNotNull(bill2.getId());

    }

    @AfterClass
    public void afterClass() {
        for(BillItem billItem : dao.findAll()) {
            dao.delete(billItem.getId());
        }
        Assert.assertTrue(dao.findAll().isEmpty());
        for(Bill bill : billDao.findAll() ) {
            billDao.delete(bill.getId());
        }
        Assert.assertTrue(billDao.findAll().isEmpty());
        for(Shop shop : shopDao.findAll()) {
            shopDao.delete(shop.getId());
        }
        Assert.assertTrue(shopDao.findAll().isEmpty());

        for(Product product : productDao.findAll()) {
            productDao.delete(product.getId());
        }
        Assert.assertTrue(productDao.findAll().isEmpty());
    }

    @Test
    @Override
    public void testFindAll() {
        List<BillItem> items = dao.findAll();
        Assert.assertNotNull(items);
    }

    //@TODO: implement
    @Override
    protected void assertEntity(BillItem entity) {

    }

    @Override
    protected void assertUpdatedEntity(BillItem expected, BillItem updated) {
        Assert.assertEquals(expected.getBillId(),updated.getBillId());
        Assert.assertEquals(expected.getProductId(), updated.getProductId());
        Assert.assertEquals(expected.getPrice(), updated.getPrice());
        Assert.assertEquals(expected.getAmount(), updated.getAmount());
    }

    @Autowired
    @Override
    protected void setDao(BillItemDao dao) {
        super.dao = dao;
    }

    @Override
    protected BillItem createEntity() {
        BillItem item = TestEntityFactory.createBillItem(bills.get(DEFAULT_INDEX).getId(),
                products.get(DEFAULT_INDEX).getId(),
                TEST_PRICE, (double)TEST_COUNT);
        return item;
    }

    @Override
    protected void updateEntity(BillItem entity) {
        entity.setProductId(products.get(DEFAULT_INDEX + 1).getId());
        entity.setBillId(bills.get(DEFAULT_INDEX + 1).getId());
        entity.setAmount((double)UPDATED_TEST_COUNT);
        entity.setPrice(UPDATED_TEST_PRICE);
    }
}
