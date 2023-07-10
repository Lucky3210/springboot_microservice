package com.sourcecode.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

//    this will create a bean of type WebClient, and we define the bean with the method name
    @Bean
    @LoadBalanced // adds a client side load balancing capabilities to the webclient builder
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
