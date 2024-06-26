package org.example.transactionservice.repository;

import org.example.transactionservice.dto.transaction.MessageUpdateBalanceTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TestMessageUpdateBalanceRepository {

    @Autowired
    private MessageTransactionRepository messageUpdateBalanceRepository;

    @Test
    public void findByStatus(){
        List<MessageUpdateBalanceTransaction> listMessage= messageUpdateBalanceRepository.findByStatus(false);
        int sum=0;
        for(MessageUpdateBalanceTransaction messageTransaction: listMessage){
            sum=sum+1;
        }
        System.out.println(sum);
    }
}
