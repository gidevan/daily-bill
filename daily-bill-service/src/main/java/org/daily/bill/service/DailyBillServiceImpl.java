package org.daily.bill.service;

import org.daily.bill.api.dao.*;
import org.daily.bill.api.service.DailyBillService;
import org.daily.bill.domain.Bill;
import org.daily.bill.domain.BillItem;
import org.daily.bill.domain.Product;
import org.daily.bill.domain.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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
        billDao.create(bill);

        List<BillItem> items = bill.getItems();
        for(BillItem item : items) {
            Product product = item.getProduct();
            checkProduct(product);
            item.setBillId(bill.getId());
            item.setProductId(product.getId());
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
        return billDao.findById(id);
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
