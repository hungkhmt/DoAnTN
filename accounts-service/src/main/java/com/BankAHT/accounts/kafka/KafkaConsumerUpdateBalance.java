package com.BankAHT.accounts.kafka;

import com.BankAHT.accounts.dto.AccountDto;
import com.BankAHT.accounts.dto.MessageTransaction;
import com.BankAHT.accounts.repository.MessageTransactionRepository;
import com.BankAHT.accounts.service.impl.AccountServiceImlp;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.util.backoff.FixedBackOff;

@Service
@AllArgsConstructor
public class KafkaConsumerUpdateBalance {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerUpdateBalance.class);
    private final AccountServiceImlp accountService;
    private final MessageTransactionRepository messageTransactionRepository;

    private static int dem=0;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

//    @RetryableTopic(backoff = @Backoff(delay = 2000L,multiplier = 2))
    @KafkaListener(
            topics = "balance_updates",
            groupId = "TransactionGroup",
            id = "balanceListener"
    )
    @Transactional
    public void consume(MessageTransaction messageTransaction) {
        try {
            LOGGER.info(String.format("Event message received => %s", messageTransaction));
            messageTransactionRepository.save(messageTransaction);

            // Fetch account
            AccountDto account = accountService.fetchAccount(messageTransaction.getAccountId());

            // Update balance
            Long newBalance = account.getBalance() + messageTransaction.getAmount();
            account.setBalance(newBalance);
            accountService.updateAccount(account);
            dem++;
        System.out.println("so lan nhan: "+dem);
        } catch (Exception e) {
            LOGGER.error("Error processing message", e);
            // Handle exception appropriately
            throw new RuntimeException();
        }

    }


//    @KafkaListener(id="dltGroup",topics = "balance_updates.DLT")
//    public void dltListen(MessageTransaction messageTransaction){
//        LOGGER.info("Event message received => %s", messageTransaction);
//        messageTransactionRepository.save(messageTransaction);
//
//            // Fetch account
//            AccountDto account = accountService.fetchAccount(messageTransaction.getAccountId());
//
//            // Update balance
//            Long newBalance = account.getBalance() + messageTransaction.getAmount();
//            account.setBalance(newBalance);
//            accountService.updateAccount(account);
//    }


}
