package org.example.transactionservice.kafka;

import lombok.AllArgsConstructor;
import org.example.transactionservice.service.IService.IBankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnableAccountConsumer {
    private static final Logger LOGGER= LoggerFactory.getLogger(CreateAccountConsumer.class);

    @Qualifier("bankAccountService")
    private IBankAccountService accountService;

    @KafkaListener(
            topics = "enable_account",
            groupId = "myGroup"
    )
    public void consumer(String message) {
        LOGGER.info(String.format("Event message received => enable_account %s", message));
        accountService.enableAccount(Long.parseLong(message));
    }
}
