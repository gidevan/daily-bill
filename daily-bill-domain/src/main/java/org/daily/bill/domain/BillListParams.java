package org.daily.bill.domain;

import java.util.Date;

/**
 * Created by vano on 3.5.17.
 */
public class BillListParams {
    private Date startPeriodDate;
    private Date endPeriodDate;

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
}
