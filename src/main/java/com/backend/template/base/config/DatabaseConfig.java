package com.backend.template.base.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url(EnvConst.DATABASE_URL);
        dataSourceBuilder.username(EnvConst.DATABASE_USERNAME);
        dataSourceBuilder.password(EnvConst.DATABASE_PASSWORD);
        return dataSourceBuilder.build();
    }
}
