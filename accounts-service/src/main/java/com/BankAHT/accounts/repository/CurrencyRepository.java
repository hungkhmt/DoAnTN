package com.BankAHT.accounts.repository;

import com.BankAHT.accounts.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,String> {
    Currency findByCurrencyCode(String CurrencyCode);
}
