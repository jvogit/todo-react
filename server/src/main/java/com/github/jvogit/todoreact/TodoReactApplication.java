package com.github.jvogit.todoreact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.github.jvogit.todoreact.config")
public class TodoReactApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoReactApplication.class, args);
    }
}
