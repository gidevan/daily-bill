package org.daily.bill.api.dao;

import org.daily.bill.domain.*;

import java.util.Date;
import java.util.List;

/**
 * Created by vano on 2.9.16.
 */
public interface BillDao extends CrudDao<Bill, Long> {

    List<Bill> getBills(BillListParams params);

    /**
     * TODO: Use bill object.
     * @param billId
     * @return
     */
    List<BillDetails> getBillDetails(Long billId);

    List<StatisticDetails> getStatisticByProduct(StatisticsParams params);

}
