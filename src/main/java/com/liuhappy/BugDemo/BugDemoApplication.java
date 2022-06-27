package com.liuhappy.BugDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lenovo
 */
@SpringBootApplication
@EnableScheduling
public class BugDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BugDemoApplication.class, args);
    }

}
