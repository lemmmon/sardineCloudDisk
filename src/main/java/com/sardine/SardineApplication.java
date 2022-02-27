package com.sardine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SardineApplication {
    public static void main(String[] args) {
        SpringApplication.run(SardineApplication.class, args);
    }
}
