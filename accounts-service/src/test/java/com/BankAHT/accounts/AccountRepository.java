package com.BankAHT.accounts;

import com.BankAHT.accounts.entity.Accounts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AccountRepository {
    @Autowired
    private com.BankAHT.accounts.repository.AccountRepository accountRepository;

    @Test
    public void testUpdateBalance(){
        accountRepository.updateBalanceByAccountIdAndAmount(6666666666L,50000L);
    }


    @Test
    public void testExistsById(){
        boolean check= accountRepository.existsById(1234567812L);
        System.out.println(check);
    }

    @Test
    public void testCountAccountCreateByMonth(){
        Long number= accountRepository.countAccountsCreatedByMonth(5,2024);
        System.out.println(number);
    }

    @Test
    public void testfindAllByCustomerIdAndIsActive(){
        List<Accounts> listRs= accountRepository.findAllByCustomerIdAndIsActive(1L);
        System.out.println(listRs.size());
    }
}
