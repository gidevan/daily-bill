package org.daily.bill.utils;

import org.daily.bill.domain.Bill;
import org.daily.bill.domain.Shop;

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
        return bill;
    }
}
