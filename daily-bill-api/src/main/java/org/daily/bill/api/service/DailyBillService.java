package org.daily.bill.api.service;

import org.daily.bill.domain.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
public interface DailyBillService {
    Shop getShopById(Long id);
    List<Shop> findShops();
    Long addDailyBill(Bill bill);
    Long updateDailyBill(Bill bill);
    List<Bill> getBills(BillListParams params);
    Bill getBillById(Long id);
    List<Product> getProducts();
    StatisticsInfo getDetailsByProduct(StatisticsParams params);
    BigDecimal findLastPrice(Long shopId, Long productId);
}
