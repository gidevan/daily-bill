package org.daily.bill.utils;

import org.daily.bill.domain.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by vano on 3.9.16.
 */
public final class TestEntityFactory {

    public static final long DEFAULT_CURRENCY_ID = 1;

    private TestEntityFactory() {
    }

    public static Shop createShop(String name) {
        return createShop(name, null);
    }

    public static Shop createShop(String name, Boolean isActive) {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setCreated(new Date());
        shop.setActive(isActive);
        return shop;
    }

    public static Bill createBill(Long shopId, Date date) {
        return createBill(shopId, date, DEFAULT_CURRENCY_ID);
    }

    public static Bill createBill(Long shopId, Date date, Long currencyId) {
        Bill bill = new Bill();
        bill.setDate(date);
        Shop shop = new Shop();
        shop.setId(shopId);
        bill.setShop(shop);
        Currency currency = new Currency();
        currency.setId(currencyId);
        bill.setCurrency(currency);
        return bill;
    }

    public static BillItem createBillItem(Long billId, Long productId, BigDecimal price, Double amount) {
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

    public static Currency createCurrency(String code, String name, Boolean isDefault) {
        Currency currency = new Currency();
        currency.setName(name);
        currency.setCode(code);
        currency.setDefaultCurrency(isDefault);
        return currency;
    }

    public static ShopRating createShopRating(Long shopId, Double rating) {
        ShopRating shopRating = new ShopRating();
        shopRating.setShopId(shopId);
        shopRating.setRating(rating);
        return shopRating;
    }
}
