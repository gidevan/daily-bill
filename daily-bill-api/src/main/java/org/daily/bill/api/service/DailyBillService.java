package org.daily.bill.api.service;

import org.daily.bill.domain.Bill;
import org.daily.bill.domain.Shop;

import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
public interface DailyBillService {
    Shop getShopById(Long id);
    List<Shop> findShops();
    Long addDailyBill(Bill bill);
}
