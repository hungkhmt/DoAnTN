package com.BankAHT.accounts.service.impl;

import com.BankAHT.accounts.entity.Currency;
import com.BankAHT.accounts.repository.CurrencyRepository;
import com.BankAHT.accounts.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements ICurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Override
    public Currency getCurrency(String code) {
        Currency currency= currencyRepository.findByCurrencyCode(code);

        return currency;
    }
}
