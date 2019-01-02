package org.daily.bill.service;

import org.daily.bill.domain.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by vano on 31.7.16.
 */
public interface DailyBillService {
    Shop getShopById(Long id);
    List<Shop> findShops();
    Long addDailyBill(Bill bill);
    List<Bill> getBills(BillListParams params);
    Bill getBillById(Long id);
    List<Product> getProducts();
    ClientStatisticsDetails getDetailsByProduct(StatisticsParams params);
    ClientStatisticsDetails getDetailsByShop(StatisticsParams params);
    BigDecimal findLastPrice(Long shopId, Long productId);
}
