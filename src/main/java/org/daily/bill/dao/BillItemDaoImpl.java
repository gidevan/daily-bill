package org.daily.bill.dao;

import org.daily.bill.domain.BillItem;
import org.springframework.stereotype.Repository;

/**
 * Created by vano on 3.9.16.
 */
@Repository
public class BillItemDaoImpl extends AbstractCrudDao<BillItem, Long> implements BillItemDao {
    @Override
    protected String getNamespace() {
        return "BillItemDao";
    }
}
