package com.example.famback;

import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DBConfig {
    private static final String DB_DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://dotorim.com:7504/todolist?characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String DB_USERNAME = "study";
    private static final String DB_PASSWORD = "wiselab";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER_CLASS_NAME);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
