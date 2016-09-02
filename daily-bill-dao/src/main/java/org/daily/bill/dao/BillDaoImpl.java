package org.daily.bill.dao;

import org.daily.bill.api.dao.BillDao;
import org.daily.bill.domain.Bill;
import org.springframework.stereotype.Repository;

/**
 * Created by vano on 2.9.16.
 */
@Repository
public class BillDaoImpl extends AbstractCrudDao<Bill, Long> implements BillDao {
    @Override
    protected String getNamespace() {
        return "BillDao";
    }
}
