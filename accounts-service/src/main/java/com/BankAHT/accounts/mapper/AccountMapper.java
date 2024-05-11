package com.BankAHT.accounts.mapper;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.entity.Accounts;

public class AccountMapper {

    public static Accounts AccountDtoToAccount(AccountDto accountDto){
        Accounts accounts= new Accounts();
        accounts.setAccountType(accountDto.getAccountType());
        accounts.setAccountId(accountDto.getAccountId());
        accounts.setCustomerId(accountDto.getCustomerId());
        accounts.setBalance(accountDto.getBalance());
        return  accounts;
    }

    public static AccountDto AccounttoAccountDto(Accounts accounts){
        AccountDto accountDto= new AccountDto();
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setCustomerId(accounts.getCustomerId());
        accountDto.setAccountId(accounts.getAccountId());
        accountDto.setBalance(accounts.getBalance());
        return accountDto;
    }
}
