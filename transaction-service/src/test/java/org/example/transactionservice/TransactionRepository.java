package org.example.transactionservice;


import org.example.transactionservice.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TransactionRepository {
    @Autowired
    private org.example.transactionservice.repository.TransactionRepository transactionRepository;

    @Test
    public void testSave(){
        List<Transaction> transactionList=transactionRepository.findByIdAccounts(1926734002L);
        for(Transaction s: transactionList){
            System.out.println(s.getTransactionId());
        }
    }

    @Test
    public void findTransactionByCustomerId(){
        List<Transaction> list= transactionRepository.findByUserId(1L);
        System.out.println(list.size());
    }


}
