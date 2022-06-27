package com.liuhappy.BugDemo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Grin
 * @Date 2022/6/27
 * @Description
 */
@Component
public class TaskService {
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void task() {
        System.out.println("Thread Name : "
                + Thread.currentThread().getName() + "  i am a task : date ->  "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
