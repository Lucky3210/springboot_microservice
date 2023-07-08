package com.sourcecode.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

//    this will create a bean of type WebClient, and we define the bean with the method name
    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
