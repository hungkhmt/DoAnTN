package com.BankAHT.accounts.kafka;

import com.BankAHT.accounts.repository.MessageTransactionRepository;
import com.BankAHT.accounts.service.impl.AccountServiceImlp;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@Configuration
public class KafkaProducerUpdateAccountConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerUpdateBalance.class);


    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryOldUpdate());
    }


    @Bean
    public ProducerFactory<String, Object> producerFactoryOldUpdate() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Sử dụng JsonSerializer cho đối tượng MessageUpdateAccount
        return new DefaultKafkaProducerFactory<>(config);
    }
//    @Transactional
//    public void producerMessageTransaction(){
//        List<MessageUpdateBalanceTransaction> listMessage= messageTransactionRepository.findByStatus(false);
//        for(MessageUpdateBalanceTransaction messageTransaction: listMessage){
//            logger.info("Sending message " + messageTransaction);
////            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("balance_updates",messageTransaction);
//            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("balance_updates",messageTransaction);
//            future.whenComplete((result, error) -> {
//                if (error != null) {
//                    error.printStackTrace();
//                    logger.error("Fail !");
//                } else {
//                    messageTransaction.setStatus(true);
//                    messageTransactionRepository.save(messageTransaction);
//                    logger.info("Success !");
//                    //set status message in database is true and remove message have status true in database
//                }
//            });
//        }
//    }

}
