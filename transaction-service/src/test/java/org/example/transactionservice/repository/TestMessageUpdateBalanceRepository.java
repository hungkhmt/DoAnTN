package org.example.transactionservice.repository;

import org.example.transactionservice.dto.transaction.MessageTransaction;
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
        List<MessageTransaction> listMessage= messageUpdateBalanceRepository.findByStatus(false);
        int sum=0;
        for(MessageTransaction messageTransaction: listMessage){
            sum=sum+1;
        }
        System.out.println(sum);
    }
}
