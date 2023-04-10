package com.chicken.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/", "classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///home/ubuntu/apps/Chicken/target/upload/");
    }
}
