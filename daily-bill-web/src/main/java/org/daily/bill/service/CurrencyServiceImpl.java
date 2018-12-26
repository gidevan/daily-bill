package org.daily.bill.service;

import org.daily.bill.dao.CurrencyDao;
import org.daily.bill.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vano on 31.8.18.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public void create(Currency entity) {
        currencyDao.create(entity);
    }

    @Override
    public int update(Currency entity) {
        return currencyDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        currencyDao.delete(id);
    }

    @Override
    public Currency findById(Long id) {
        return currencyDao.findById(id);
    }

    @Override
    public List<Currency> findAll() {
        return currencyDao.findAll();
    }
}
