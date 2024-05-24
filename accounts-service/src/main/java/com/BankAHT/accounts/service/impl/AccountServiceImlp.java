package com.BankAHT.accounts.service.impl;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.dto.MessageUpdateAccount;
import com.BankAHT.accounts.entity.AccountStatus;
import com.BankAHT.accounts.entity.Accounts;
import com.BankAHT.accounts.exception.ResourceNoFoundException;
import com.BankAHT.accounts.mapper.AccountMapper;
import com.BankAHT.accounts.repository.AccountRepository;
import com.BankAHT.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import jakarta.transaction.Transactional;
@Service
public class AccountServiceImlp implements IAccountService {

    @Autowired
    private KafkaTemplate<String,MessageUpdateAccount> kafkaTemplateUpdateAccount;
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;


    @Autowired
    private AccountRepository accountRepository;

//    @Transactional
//    @Override
//    public void createAccount(AccountDto accountDto) {
//        Accounts accounts= AccountMapper.AccountDtoToAccount(accountDto);
//        accounts.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//        accounts.setCreatedBy("PTD-PTIT");
//        accounts.setEnable(true);
//        Accounts accountsSave=accountRepository.save(accounts);
//        MessageUpdateAccount messageUpdateAccount= MessageUpdateAccount.builder()
//                .AccountType(accountsSave.getAccountType())
//                .CustomerId(accounts.getCustomerId())
//                .AccountId(accountsSave.getAccountId())
//                .enable(accountsSave.getEnable())
//                .build();
//        kafkaTemplateUpdateAccount.send("create_account",messageUpdateAccount);
//  }
    @Transactional
    @Override
    public void createAccount(AccountDto accountDto) {
        // tạo tài khoan thi account se co 50k
        accountDto.setBalance(50_000L);


        Accounts accounts= AccountMapper.AccountDtoToAccount(accountDto);
        accounts.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        accounts.setCreatedBy("PTD-PTIT");
        accounts.setStatus(AccountStatus.PENDING);
        accounts.setBalance(50_000L);
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
    public List<AccountDto> getAllAccount() {
        List<Accounts> accounts = accountRepository.findAll();
        List<AccountDto> accountDtos = new ArrayList<>();
        for(Accounts accounts1: accounts) {
            accountDtos.add(accountToAccountDTO(accounts1));
        }
        return accountDtos;
    }

    @Override
    public boolean updateAccount(AccountDto accountDto) {

        Accounts oldAccouts= accountRepository.findById(accountDto.getAccountId()).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountDto.getAccountId().toString(),"AccoutsNumber"));
        Accounts accounts= AccountMapper.AccountDtoToAccount(accountDto);
        accounts.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accounts.setUpdatedBy("PTD-PTIT");
        accounts.setStatus(oldAccouts.getStatus());
        accountRepository.save(accounts);
        return false;
    }

    @Transactional
    @Override
    public boolean deleteAccount(Long accountNumber) {
      Accounts accounts= accountRepository.findById(accountNumber).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountNumber.toString(),"AccoutsNumber"));
      accounts.setStatus(AccountStatus.INACTIVE);
        accounts.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accounts.setUpdatedBy("PTD-PTIT");
      accountRepository.save(accounts);
      MessageUpdateAccount messageUpdateAccount= new MessageUpdateAccount();
      messageUpdateAccount.setAccountId(accountNumber);
      kafkaTemplate.send("delete_account",accountNumber);
      return true;
    }

    @Transactional
    @Override
    public void enableAccount(Long accountNumber) {
        Accounts accounts= accountRepository.findById(accountNumber).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountNumber.toString(),"AccoutsNumber"));
        accounts.setStatus(AccountStatus.ACTIVE);
        accounts.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accounts.setUpdatedBy("PTD-PTIT");
        accountRepository.save(accounts);
        kafkaTemplate.send("enable_account",accountNumber);
    }

//    delete_account
//            enable_account

    @Transactional
    public void producerMessageUpdateAccountTransaction(MessageUpdateAccount messageUpdateAccount){
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("account_updates",messageUpdateAccount);
    }

    public Long getUserIdByAccountId(Long accountId) {
        return accountRepository.findCustomerIdByAccountId(accountId);
    }

    private AccountDto accountToAccountDTO(Accounts accounts) {
        return AccountDto.builder()
                .accountId(accounts.getAccountId())
                .customerId(accounts.getCustomerId())
                .accountType(accounts.getAccountType())
                .accountStatus(accounts.getStatus())
                .balance(accounts.getBalance())
                .createdAt(accounts.getCreatedAt())
                .build();
    }

}
