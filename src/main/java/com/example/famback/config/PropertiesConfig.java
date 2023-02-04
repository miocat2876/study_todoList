package com.example.famback.config;

import com.example.famback.properties.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {FileProperties.class})
public class PropertiesConfig {
}
