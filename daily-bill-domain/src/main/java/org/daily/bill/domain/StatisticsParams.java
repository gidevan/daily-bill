package org.daily.bill.domain;

import java.util.Date;

/**
 * Created by vano on 10.4.17.
 */
public class StatisticsParams {

    private Date startPeriodDate;
    private Date endPeriodDate;
    private String productName;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
