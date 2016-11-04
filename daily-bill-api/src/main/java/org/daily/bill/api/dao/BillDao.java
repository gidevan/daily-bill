package org.daily.bill.api.dao;

import org.daily.bill.domain.Bill;

import java.util.List;

/**
 * Created by vano on 2.9.16.
 */
public interface BillDao extends CrudDao<Bill, Long> {

    List<Bill> getBills();
}
