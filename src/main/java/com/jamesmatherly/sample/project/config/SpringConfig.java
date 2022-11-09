package com.jamesmatherly.sample.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    @Bean
    RestTemplate template () { 
        return new RestTemplate();
    }
    
}
