package com.backend.template;

import java.util.Collections;

import com.backend.template.base.config.EnvConst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@EnableTransactionManagement
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TemplateApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", EnvConst.SERVER_PORT));
        app.run(args);
    }
}
