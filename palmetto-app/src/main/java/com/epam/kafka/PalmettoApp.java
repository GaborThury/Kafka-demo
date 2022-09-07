package com.epam.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class PalmettoApp {
    public static void main(String[] args) {
        SpringApplication.run(PalmettoApp.class, args);
    }

}
