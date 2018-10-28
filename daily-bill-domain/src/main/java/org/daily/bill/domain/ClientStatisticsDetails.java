package org.daily.bill.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by vano on 10.10.18.
 */
public class ClientStatisticsDetails {

    private Map<String, StatisticsInfo> statisticInfo;
    private List<String> currencies;

    public Map<String, StatisticsInfo> getStatisticInfo() {
        return statisticInfo;
    }

    public void setStatisticInfo(Map<String, StatisticsInfo> statisticInfo) {
        this.statisticInfo = statisticInfo;
    }

    public List<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<String> currencies) {
        this.currencies = currencies;
    }
}

