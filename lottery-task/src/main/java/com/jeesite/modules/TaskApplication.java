package com.jeesite.modules;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
