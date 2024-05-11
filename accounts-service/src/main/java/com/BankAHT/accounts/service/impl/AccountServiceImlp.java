package com.BankAHT.accounts.service.impl;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.entity.Accounts;
import com.BankAHT.accounts.exception.ResourceNoFoundException;
import com.BankAHT.accounts.mapper.AccountMapper;
import com.BankAHT.accounts.repository.AccountRepository;
import com.BankAHT.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import jakarta.transaction.Transactional;
@Service
public class AccountServiceImlp implements IAccountService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;


    @Autowired
    private AccountRepository accountRepository;
    @Transactional
    @Override
    public void createAccount(AccountDto accountDto) {
        Accounts accounts= AccountMapper.AccountDtoToAccount(accountDto);
        accounts.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        accounts.setCreatedBy("PTD-PTIT");
        accounts.setEnable(true);
        accountRepository.save(accounts);
        kafkaTemplate.send("create_account",accountDto.toString());
  }

    @Override
    public AccountDto fetchAccount(Long accountNumber) {
       Accounts accounts= accountRepository.findById(accountNumber).orElseThrow(()->new ResourceNoFoundException("Account","accountNumber",accountNumber.toString()));
       AccountDto accountDto= AccountMapper.AccounttoAccountDto(accounts);
       return  accountDto;
    }

    @Override
    public boolean updateAccount(AccountDto accountDto) {

        Accounts oldAccouts= accountRepository.findById(accountDto.getAccountId()).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountDto.getAccountId().toString(),"AccoutsNumber"));
        Accounts accounts= AccountMapper.AccountDtoToAccount(accountDto);
        accounts.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accounts.setUpdatedBy("PTD-PTIT");
        accounts.setEnable(oldAccouts.getEnable());
        accountRepository.save(accounts);
        return false;
    }

    @Transactional
    @Override
    public boolean deleteAccount(Long accountNumber) {
      Accounts accounts= accountRepository.findById(accountNumber).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountNumber.toString(),"AccoutsNumber"));
      accounts.setEnable(false);
      accountRepository.save(accounts);
      kafkaTemplate.send("delete_account",accountNumber.toString());
      return true;
    }

    @Transactional
    @Override
    public void enableAccount(Long accountNumber) {
        Accounts accounts= accountRepository.findById(accountNumber).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountNumber.toString(),"AccoutsNumber"));
        accounts.setEnable(true);
        accountRepository.save(accounts);
        kafkaTemplate.send("enable_account",accountNumber.toString());
    }
}
