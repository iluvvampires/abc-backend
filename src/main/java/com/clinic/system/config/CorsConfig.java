package com.clinic.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Your React URL
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Make sure PATCH is here!
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}