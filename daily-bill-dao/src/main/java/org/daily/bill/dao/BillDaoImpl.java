package org.daily.bill.dao;

import org.daily.bill.api.dao.BillDao;
import org.daily.bill.domain.Bill;
import org.daily.bill.domain.BillDetails;
import org.springframework.stereotype.Repository;

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
}
