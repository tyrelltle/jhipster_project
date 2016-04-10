package com.hak.haklist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration for server side routes.
 * By default, spring boot maps '/' to index.html. however we need
 * additional routes, for example /app to index-haklist.html. this config
 * class lets you add those additional routes
 */
@Configuration
public class WebMvcConfig {

    public static final int MAX_UPLOAD_SIZE = 50 * 1024 * 1024; //50MB

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // forward requests to /admin and /user to their index.html

                registry.addViewController("/app").setViewName(
                    "forward:/index-haklist.html");
            }
        };
    }

    @Bean
    public MultipartResolver getMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
       // resolver.setMaxInMemorySize(MAX_UPLOAD_SIZE);
        return resolver;
    }

}
