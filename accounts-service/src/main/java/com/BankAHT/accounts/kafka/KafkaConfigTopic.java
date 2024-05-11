package com.BankAHT.accounts.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfigTopic {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("create_account")
                .build();
    }

    @Bean
    public NewTopic deleteAccountTopic() {
        return TopicBuilder.name("delete_account")
                .build();
    }

    @Bean
    public NewTopic enableAccountTopic() {
        return TopicBuilder.name("enable_account")
                .build();
    }

}

