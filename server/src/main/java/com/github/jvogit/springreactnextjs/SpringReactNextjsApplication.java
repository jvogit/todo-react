package com.github.jvogit.springreactnextjs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.github.jvogit.springreactnextjs.config")
public class SpringReactNextjsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringReactNextjsApplication.class, args);
    }
}
