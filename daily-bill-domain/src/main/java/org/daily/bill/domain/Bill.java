package org.daily.bill.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vano on 2.9.16.
 */
public class Bill implements Identifiable<Long> {

    private Long id;
    private Date date;
    private Shop shop;
    private List<BillItem> items = new ArrayList<>();
    private Date created;
    private Date updated;
    private BigDecimal billSum;
    private Currency currency;
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public BigDecimal getBillSum() {
        return billSum;
    }

    public void setBillSum(BigDecimal billSum) {
        this.billSum = billSum;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
