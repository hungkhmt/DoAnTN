package org.example.transactionservice.repository;

import org.example.transactionservice.common.AccountType;
import org.example.transactionservice.model.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    public void isBankAccountExist() {

        BankAccount bankAccount = BankAccount.builder()
                .accountId(1L)
                .accountType(AccountType.CHECKOUT)
                .balance(40000D)
                .userId(1L)
                .build();

        bankAccountRepository.save(bankAccount);

        BankAccount expected = bankAccountRepository.findBankAccoutById(1L);

        assertNotNull(expected, "can not be null");
    }
}