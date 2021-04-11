package com.backend.template;

import java.util.Collections;

import com.backend.template.base.config.EnvConst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TemplateApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TemplateApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", EnvConst.SERVER_PORT));
        app.run(args);
    }
}
