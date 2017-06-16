package org.daily.bill.dao;

import org.daily.bill.api.dao.BillDao;
import org.daily.bill.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Date;
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
}
