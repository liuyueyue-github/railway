package com.liuhappy.beta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lenovo
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.liuhappy.beta.mapper")
public class BetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetaApplication.class, args);
    }

}
