package com.BankAHT.accounts.service;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.dto.MessageUpdateAccount;

import java.util.List;

public interface IAccountService {
    void createAccount(AccountDto accountDto);
    AccountDto fetchAccount(Long accountNumber);
    boolean updateAccount(AccountDto accountDto);
    public List<AccountDto> getAllAccount();
    boolean deleteAccount(Long accountNumber);
    void enableAccount(Long accountNumber);
    void producerMessageUpdateAccountTransaction(MessageUpdateAccount messageUpdateAccount);
    public Long getUserIdByAccountId(Long accountId);

}
