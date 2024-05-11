package com.aht.UserManagementService.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;


@Configuration
public class InternationalizationConfiguration {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message"); // Tên của file message.properties, ví dụ: messages.properties
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
}
}
