package com.toy.subkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
public class SubkeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubkeeperApplication.class, args);
    }

}

