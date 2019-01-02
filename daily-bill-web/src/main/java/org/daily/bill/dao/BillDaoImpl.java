package org.daily.bill.dao;

import org.daily.bill.domain.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vano on 2.9.16.
 */
@Repository
public class BillDaoImpl extends AbstractCrudDao<Bill, Long> implements BillDao {
    @Override
    protected String getNamespace() {
        return "BillDao";
    }

    @Override
    public List<Bill> getBills(BillListParams params) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("startPeriodDate", params.getStartPeriodDate());
        queryParams.put("endPeriodDate", params.getEndPeriodDate());
        return getSqlSession().selectList(getNamespace() + ".getBills", queryParams);
    }

    @Override
    public List<BillDetails> getBillDetails(Long billId) {
        Map<String, Object> params = new HashMap<>();
        params.put("billId", billId);
        return getSqlSession().selectList(getNamespace() + ".getBillDetails", params);
    }

    @Override
    public List<StatisticDetails> getStatisticByProduct(StatisticsParams params) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("startPeriodDate", params.getStartPeriodDate());
        queryParams.put("endPeriodDate", params.getEndPeriodDate());
        if(!params.getProductNames().isEmpty()) {
            List<String> likeCondition = params.getProductNames().stream().map(n -> "%" + n.toLowerCase() + "%")
                    .collect(Collectors.toList());
            queryParams.put("productNames", likeCondition);
        }

        return getSqlSession().selectList(getNamespace() + ".getDetailsByProduct", queryParams);
    }

    @Override
    public List<StatisticDetails> getStatisticByShop(StatisticsParams params) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("startPeriodDate", params.getStartPeriodDate());
        queryParams.put("endPeriodDate", params.getEndPeriodDate());
        if(!params.getShopNames().isEmpty()) {
            List<String> likeCondition = params.getShopNames().stream().map(n -> "%" + n.toLowerCase() + "%")
                    .collect(Collectors.toList());
            queryParams.put("shopNames", likeCondition);
        }

        return getSqlSession().selectList(getNamespace() + ".getDetailsByShop", queryParams);
    }
}
