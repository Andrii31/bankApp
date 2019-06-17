package com.energizer.bank.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Bean
    AccountService simpleAccountService() {
        return new SimpleAccountService();
    }
}
