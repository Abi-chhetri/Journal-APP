package com.innovator.journal.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class QuoteApiCallConfig {

    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
