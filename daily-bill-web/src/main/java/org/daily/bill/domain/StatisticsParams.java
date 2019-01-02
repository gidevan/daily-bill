package org.daily.bill.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by vano on 10.4.17.
 */
public class StatisticsParams {

    private Date startPeriodDate;
    private Date endPeriodDate;
    private List<String> productNames;
    private List<String> shopNames;

    public Date getStartPeriodDate() {
        return startPeriodDate;
    }

    public void setStartPeriodDate(Date startPeriodDate) {
        this.startPeriodDate = startPeriodDate;
    }

    public Date getEndPeriodDate() {
        return endPeriodDate;
    }

    public void setEndPeriodDate(Date endPeriodDate) {
        this.endPeriodDate = endPeriodDate;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public List<String> getShopNames() {
        return shopNames;
    }

    public void setShopNames(List<String> shopNames) {
        this.shopNames = shopNames;
    }
}
