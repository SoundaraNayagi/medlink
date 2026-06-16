package com.medlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedlinkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedlinkBackendApplication.class, args);
    }
}