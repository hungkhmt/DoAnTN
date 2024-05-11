package org.example.transactionservice.service.IService;

import org.example.transactionservice.model.BankAccount;
import org.springframework.stereotype.Service;

@Service
public interface IBankAccountService {

    BankAccount getBankAccById(Long accountId) throws Exception;

    void updateAccountBalance(Long accountId, Double amount) throws Exception;

    void createAccount(BankAccount account);
    boolean deleteAccount(Long accountNumber);
    void enableAccount(Long accountNumber);
}
