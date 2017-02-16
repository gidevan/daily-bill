package org.daily.bill.api.dao;

import org.daily.bill.domain.Bill;
import org.daily.bill.domain.BillDetails;

import java.util.List;

/**
 * Created by vano on 2.9.16.
 */
public interface BillDao extends CrudDao<Bill, Long> {

    List<Bill> getBills();

    /**
     * TODO: Use bill object.
     * @param billId
     * @return
     */
    List<BillDetails> getBillDetails(Long billId);
}
