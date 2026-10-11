package com.lba.campaignsengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CampaignsEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampaignsEngineApplication.class, args);
    }
}
