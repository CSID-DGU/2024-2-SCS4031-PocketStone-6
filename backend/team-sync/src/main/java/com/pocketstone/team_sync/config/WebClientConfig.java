package com.pocketstone.team_sync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {



    //fastapi-파이썬
    @Bean
    public WebClient webClient_model() {
        return WebClient.builder()
            //.baseUrl("http://berich-flask-app.ap-northeast-2.elasticbeanstalk.com") //모의계좌 url
            .baseUrl("http://localhost:5000") //fastappi url
            //.defaultHeader("Authorization", "Bearer your-token")
            //.defaultHeader("content-type", "application/json")
            .build();
    }
}
