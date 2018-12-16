package org.daily.bill.service;

import org.daily.bill.dao.BillDao;
import org.daily.bill.dao.BillItemDao;
import org.daily.bill.dao.ProductDao;
import org.daily.bill.dao.ShopDao;
import org.daily.bill.domain.*;
import org.daily.bill.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        return shopDao.findShops(true);
    }

    @Override
    public Long addDailyBill(Bill bill) {
        Shop shop = bill.getShop();
        checkShop(shop);
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
    public List<Bill> getBills(BillListParams params) {
        return billDao.getBills(params);
    }

    @Override
    public Bill getBillById(Long id) {
        List<BillDetails> billDetails = billDao.getBillDetails(id);
        return createBill(billDetails);
    }

    @Override
    public List<Product> getProducts() {
        return productDao.findProducts(true);
    }

    @Override
    public ClientStatisticsDetails getDetailsByProduct(StatisticsParams params) {
        if(params.getEndPeriodDate() == null) {
            params.setEndPeriodDate(new Date());
        }
        List<StatisticDetails> details = billDao.getStatisticByProduct(params);
        Map<String, StatisticsInfo> detailsMap = details.stream()
                .collect(Collectors.groupingBy(d -> d.getCurrency().getCode()))
                .entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> new StatisticsInfo(e.getValue())));

        ClientStatisticsDetails clientStatisticsDetails = new ClientStatisticsDetails();
        List<String> currencies = new ArrayList<>();
        currencies.addAll(detailsMap.keySet());
        clientStatisticsDetails.setCurrencies(currencies);
        clientStatisticsDetails.setStatisticInfo(detailsMap);
        return clientStatisticsDetails;
    }

    @Override
    public BigDecimal findLastPrice(Long shopId, Long productId) {
        return productDao.findLastPrice(shopId, productId);
    }

    private Bill createBill(List<BillDetails> billDetails) {
        Bill bill = new Bill();
        for(BillDetails details :billDetails) {
            if (bill.getShop() == null) {
                Shop shop = new Shop();
                shop.setId(details.getShopId());
                shop.setName(details.getShopName());
                bill.setShop(shop);
            }
            if (bill.getCurrency() == null) {
                Currency currency = new Currency();
                currency.setId(details.getCurrencyId());
                currency.setCode(details.getCurrencyCode());
                currency.setName(details.getCurrencyName());
                bill.setCurrency(currency);
            }
            bill.setId(details.getBillId());
            bill.setDate(details.getDate());
            BillItem item = new BillItem();
            item.setId(details.getBillItemId());
            item.setPrice(details.getPrice());
            item.setAmount(details.getAmount());
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
