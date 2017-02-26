package org.daily.bill.service;

import org.daily.bill.api.dao.*;
import org.daily.bill.api.service.DailyBillService;
import org.daily.bill.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
@Service("dailyBillService")
public class DailyBillServiceImpl implements DailyBillService {

    private static final String WRONG_SHOP_PARAM_MSG = "Wrong shop parameter. [Id] = %1, [name] = %2";
    private static final String WRONG_PRODUCT_PARAM_MSG = "Wrong product parameter. [Id] = %1, [name] = %2";
    private static final String WRONG_PRODUCT_NAME_MSG = "Product name is empty.";
    private static final String WRONG_SHOP_NAME_MSG = "Shop name is empty.";

    @Autowired
    private BillDao billDao;

    @Autowired
    private BillItemDao billItemDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Shop getShopById(Long id) {
        return shopDao.findById(id);
    }

    @Override
    public List<Shop> findShops() {
        return shopDao.findAll();
    }

    @Override
    public Long addDailyBill(Bill bill) {
        Shop shop = bill.getShop();
        checkShop(shop);
        bill.setShopId(shop.getId());
        if(bill.getId() != null) {
            billDao.update(bill);
        } else {
            billDao.create(bill);
        }

        List<BillItem> items = bill.getItems();
        for(BillItem item : items) {
            Product product = item.getProduct();
            checkProduct(product);
            item.setBillId(bill.getId());
            item.setProductId(product.getId());
            if(item.getId() != null) {
                billItemDao.update(item);
            } else {
                billItemDao.create(item);
            }

        }
        return bill.getId();
    }

    @Override
    public Long updateDailyBill(Bill bill) {
        Shop shop = bill.getShop();
        checkShop(shop);
        bill.setShopId(shop.getId());
        bill.setUpdated(new Date());
        billDao.update(bill);

        List<BillItem> items = bill.getItems();
        for(BillItem item : items) {
            Product product = item.getProduct();
            checkProduct(product);
            item.setBillId(bill.getId());
            item.setProductId(product.getId());
            if(item.getId() != null) {
                item.setUpdated(new Date());
                billItemDao.update(item);
            } else {

            }
            billItemDao.create(item);
        }
        return bill.getId();
    }

    @Override
    public List<Bill> getBills() {
        return billDao.getBills();
    }

    @Override
    public Bill getBillById(Long id) {
        List<BillDetails> billDetails = billDao.getBillDetails(id);
        return createBill(billDetails);
    }

    private Bill createBill(List<BillDetails> billDetails) {
        Bill bill = new Bill();
        for(BillDetails details :billDetails) {
            if(bill.getShop() == null) {
                Shop shop = new Shop();
                shop.setId(details.getShopId());
                shop.setName(details.getShopName());
                bill.setShop(shop);
            }
            bill.setId(details.getBillId());
            bill.setDate(details.getDate());
            BillItem item = new BillItem();
            item.setId(details.getBillItemId());
            item.setPrice(details.getPrice());
            item.setCountItem(details.getCountItem());
            Product product = new Product();
            product.setId(details.getProductId());
            product.setName(details.getProductName());
            item.setProduct(product);
            bill.getItems().add(item);
        }
        return bill;
    }

    private void checkShop(Shop shop) {
        if(shop.getId() != null) {
            Shop stored = shopDao.findById(shop.getId());
            if(stored == null) {
                String msg = MessageFormat.format(WRONG_SHOP_PARAM_MSG, shop.getId(), shop.getName());
                throw new IllegalArgumentException(msg);
            }
        } else {
            if(shop.getName() == null) {
                throw new IllegalArgumentException(WRONG_SHOP_NAME_MSG);
            }
            shopDao.create(shop);
        }
    }

    private void checkProduct(Product product) {
        if (product.getId() != null) {
            Product stored = productDao.findById(product.getId());
            if(stored == null) {
                String msg = MessageFormat.format(WRONG_PRODUCT_PARAM_MSG, product.getId(), product.getName());
                throw new IllegalArgumentException(msg);
            }
        } else {
            if(product.getName() == null) {
                throw new IllegalArgumentException(WRONG_PRODUCT_NAME_MSG);
            }
            productDao.create(product);
        }

    }
}
