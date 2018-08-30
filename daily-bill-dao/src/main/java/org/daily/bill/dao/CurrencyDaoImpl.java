package org.daily.bill.dao;

import org.daily.bill.api.dao.CurrencyDao;
import org.daily.bill.domain.Currency;
import org.springframework.stereotype.Repository;

/**
 * Created by vano on 21.8.18.
 */
@Repository
public class CurrencyDaoImpl extends AbstractCrudDao<Currency, Long> implements CurrencyDao {

    @Override
    public Currency getDefaultCurrency() {
        return null;
    }

    @Override
    protected String getNamespace() {
        return "CurrencyDao";
    }
}
