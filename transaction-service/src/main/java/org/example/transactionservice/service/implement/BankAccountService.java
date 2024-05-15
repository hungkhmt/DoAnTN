package org.example.transactionservice.service.implement;

import jakarta.transaction.Transactional;
import org.example.transactionservice.dto.transaction.MessageUpdateBalanceTransaction;
import org.example.transactionservice.exception.AccountIsNotValidException;
import org.example.transactionservice.exception.ResourceNoFoundException;
import org.example.transactionservice.model.BankAccount;
import org.example.transactionservice.repository.BankAccountRepository;
import org.example.transactionservice.repository.MessageTransactionRepository;
import org.example.transactionservice.service.IService.IBankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService implements IBankAccountService {

    private KafkaTemplate<String, Object> kafkaTemplate;
    private final BankAccountRepository bankAccountRepository;
    private Logger logger= LoggerFactory.getLogger(BankAccountService.class);

    @Autowired
    private MessageTransactionRepository messageUpdateBalanceRepository;

    public BankAccountService(KafkaTemplate<String, Object> kafkaTemplate, BankAccountRepository bankAccountRepository){
        this.kafkaTemplate= kafkaTemplate;
        this.bankAccountRepository=bankAccountRepository;
    }


    @Override
    public BankAccount getBankAccById(Long accountId) throws Exception {
        BankAccount bankAccount = bankAccountRepository.findBankAccoutById(accountId);
        if (bankAccount != null) {
            return bankAccount;
        }
        throw new AccountIsNotValidException("Account is not exist");
    }

    @Transactional
    @Override
    public void updateAccountBalance(Long accountId, Double amount) throws Exception {
        BankAccount bankAccount = getBankAccById(accountId);

        double updatedBalance = bankAccount.getBalance() + amount;

        bankAccount.setBalance(updatedBalance);
        bankAccountRepository.save(bankAccount);
       MessageUpdateBalanceTransaction messageTransaction= new MessageUpdateBalanceTransaction(accountId,amount.longValue());
       messageTransaction.setStatus(false);
       messageUpdateBalanceRepository.save(messageTransaction);

    }

    @Override
    public void createAccount(BankAccount account) {
        BankAccount accountSave= new BankAccount();
        accountSave.setAccountId(account.getAccountId());
        accountSave.setAccountType(account.getAccountType());
        accountSave.setUserId(account.getUserId());
        accountSave.setBalance(account.getBalance());
        accountSave.setEnable(true);
        bankAccountRepository.save(accountSave);
    }

    @Override
    public boolean deleteAccount(Long accountNumber) {
        BankAccount accounts= bankAccountRepository.findById(accountNumber).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountNumber.toString(),"AccoutsNumber"));
        accounts.setEnable(false);
        bankAccountRepository.save(accounts);
        return true;
    }

    @Override
    public void enableAccount(Long accountNumber) {
        BankAccount accounts= bankAccountRepository.findById(accountNumber).orElseThrow(()-> new ResourceNoFoundException("Accouts",accountNumber.toString(),"AccoutsNumber"));
        accounts.setEnable(true);
        bankAccountRepository.save(accounts);
    }


}
