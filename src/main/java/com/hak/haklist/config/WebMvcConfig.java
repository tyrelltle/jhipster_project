package com.hak.haklist.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * Configuration for server side routes.
 * By default, spring boot maps '/' to index.html. however we need
 * additional routes, for example /app to index-haklist.html. this config
 * class lets you add those additional routes
 */
@Configuration
public class WebMvcConfig {

    public static final int MAX_UPLOAD_SIZE = 50 * 1024 * 1024; //50MB
    private final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

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

   /* @Bean
    public MultipartResolver getMultipartResolver() {
        log.debug("========entering WebMvcConfig.getMultipartResolve()");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
       // resolver.setMaxInMemorySize(MAX_UPLOAD_SIZE);
        return resolver;
    }
    */

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("50MB");
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }

}
