package com.tsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EnvironmentalMonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnvironmentalMonitoringApplication.class, args);
    }

}

