package org.daily.bill.api.dao;

import org.daily.bill.domain.Currency;

/**
 * Created by vano on 20.8.18.
 */
public interface CurrencyDao extends CrudDao<Currency, Long> {

    Currency getDefaultCurrency();
}
