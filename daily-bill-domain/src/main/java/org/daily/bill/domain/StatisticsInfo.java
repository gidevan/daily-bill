package org.daily.bill.domain;

import java.math.BigDecimal;
import java.util.List;


public class StatisticsInfo {
    private BigDecimal totalSum = BigDecimal.ZERO;
    private List<StatisticDetails> statisticDetails;

    public StatisticsInfo() {
    }

    public StatisticsInfo(List<StatisticDetails> statisticDetails) {
        this.statisticDetails = statisticDetails;
        statisticDetails.forEach(sd -> {
            totalSum = totalSum.add(sd.getPrice());
        });
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public List<StatisticDetails> getStatisticDetails() {
        return statisticDetails;
    }

    public void setStatisticDetails(List<StatisticDetails> statisticDetails) {
        this.statisticDetails = statisticDetails;
    }
}
