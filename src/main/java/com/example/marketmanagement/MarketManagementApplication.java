package com.example.marketmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class MarketManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketManagementApplication.class, args);
    }
}
