package com.github.yuzhian.zero.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.yuzhian.zero.boot.**")
public class ZeroBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroBootApplication.class, args);
    }

}
