package org.daily.bill.utils;

import org.daily.bill.domain.Bill;
import org.daily.bill.domain.BillItem;
import org.daily.bill.domain.Product;
import org.daily.bill.domain.Shop;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by vano on 3.9.16.
 */
public final class TestEntityFactory {

    private TestEntityFactory() {
    }

    public static Shop createShop(String name) {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setCreated(new Date());
        return shop;
    }

    public static Bill createBill(Long shopId, Date date) {
        Bill bill = new Bill();
        bill.setDate(date);
        bill.setShopId(shopId);
        Shop shop = new Shop();
        shop.setId(shopId);
        bill.setShop(shop);
        return bill;
    }

    public static BillItem cretaeBillItem(Long billId, Long productId, BigDecimal price, Double amount) {
        BillItem billItem = new BillItem();
        billItem.setBillId(billId);
        billItem.setProductId(productId);
        billItem.setPrice(price);
        billItem.setAmount(amount);
        billItem.setCreated(new Date());
        return billItem;
    }

    public static Product createProduct(String name, String description) {
        Product product= new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCreated(new Date());
        return product;
    }
}
