package com.BankAHT.accounts.service;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.dto.MessageUpdateAccount;
import com.BankAHT.accounts.entity.Accounts;

import java.util.HashMap;
import java.util.List;

public interface IAccountService {
    Accounts createAccount(AccountDto accountDto);
    AccountDto fetchAccount(Long accountNumber);
    List<AccountDto> getAllAccountByUserId(Long userId);
    List<AccountDto> getAllAccountByMonth(Integer month);
    boolean updateAccount(AccountDto accountDto);
    void updateBalance(Long idAccount,Long amount);
    public List<AccountDto> getAllAccount();
    boolean deleteAccount(Long accountNumber);
    void enableAccount(Long accountNumber);
    void producerMessageUpdateAccountTransaction(MessageUpdateAccount messageUpdateAccount);
    Long getUserIdByAccountId(Long accountId);
    public Long getUserIdByAccountId(Long accountId);
    public HashMap<Integer,Long> accountCreationStatistics();
}
