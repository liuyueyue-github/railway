package com.liuhappy.ControllerDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lenovo
 */
@SpringBootApplication
@EnableScheduling
public class ControllerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControllerDemoApplication.class, args);
    }

}
