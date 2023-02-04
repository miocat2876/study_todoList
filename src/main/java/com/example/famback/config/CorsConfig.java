package com.example.famback.config;

import com.example.famback.util.PropertyUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
			    .allowedOriginPatterns(PropertyUtil.getProperty("origin.address"))//PropertyUtil.getProperty("origin.address")
			    .allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*")
				.exposedHeaders("Set-Cookie")
				.exposedHeaders("Authorization")
			    .allowCredentials(true);
    }
}
