package com.BankAHT.accounts.service;

import com.BankAHT.accounts.entity.Currency;

public interface ICurrencyService {
    Currency getCurrency(String code);
}
