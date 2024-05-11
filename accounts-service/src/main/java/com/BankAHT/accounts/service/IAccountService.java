package com.BankAHT.accounts.service;

import com.BankAHT.accounts.dto.AccountDto;

public interface IAccountService {
    void createAccount(AccountDto accountDto);
    AccountDto fetchAccount(Long accountNumber);
    boolean updateAccount(AccountDto accountDto);

    boolean deleteAccount(Long accountNumber);
    void enableAccount(Long accountNumber);
}
