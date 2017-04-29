package org.daily.bill.dao;

import org.daily.bill.api.dao.BillDao;
import org.daily.bill.domain.Bill;
import org.daily.bill.domain.BillDetails;
import org.daily.bill.domain.StatisticDetails;
import org.daily.bill.domain.StatisticsParams;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Bill> getBills() {
        return getSqlSession().selectList(getNamespace() + ".getBills");
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
        if(!StringUtils.isEmpty(params.getProductName())) {
            queryParams.put("productName", "%" + params.getProductName().toLowerCase() + "%");
        }

        return getSqlSession().selectList(getNamespace() + ".getDetailsByProduct", queryParams);
    }
}
