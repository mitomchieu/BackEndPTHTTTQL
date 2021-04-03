package com.backend.template;

import java.util.Collections;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemplateApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TemplateApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", 3323));
        app.run(args);
    }
}
