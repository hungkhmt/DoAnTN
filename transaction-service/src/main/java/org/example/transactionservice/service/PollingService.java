package org.example.transactionservice.service;

import jakarta.transaction.Transactional;
import org.example.transactionservice.dto.transaction.MessageTransaction;
import org.example.transactionservice.repository.MessageTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class PollingService {

    private Logger logger= LoggerFactory.getLogger(PollingService.class);
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    private MessageTransactionRepository messageTransactionRepository;

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void producerMessageTransaction(){
        List<MessageTransaction> listMessage= messageTransactionRepository.findByStatus(false);
        for(MessageTransaction messageTransaction: listMessage){
            logger.info("Sending message " + messageTransaction);
//            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("balance_updates",messageTransaction);
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("balance_updates",messageTransaction);
            future.whenComplete((result, error) -> {
                if (error != null) {
                    error.printStackTrace();
                   logger.error("Fail !");
                } else {
                   messageTransaction.setStatus(true);
                   messageTransactionRepository.save(messageTransaction);
                    logger.info("Success !");
                    //set status message in database is true and remove message have status true in database
                }
        });
    }
    }

//    @Scheduled(fixedDelay = 5000)
//    public void deleteMessageSendSuccess(){
//        List<MessageTransaction> listMessage= messageTransactionRepository.findByStatus(true);
//        messageTransactionRepository.deleteAllInBatch(listMessage);
//    }
}
