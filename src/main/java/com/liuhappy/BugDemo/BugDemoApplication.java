package com.liuhappy.BugDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lenovo
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.liuhappy.BugDemo.mapper")
public class BugDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BugDemoApplication.class, args);
    }

}
