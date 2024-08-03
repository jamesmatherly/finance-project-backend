package com.jamesmatherly.sample.project.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    @Bean
    RestTemplate template () { 
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean<TokenUppercaseFilter> tokenUppercaseFilter() {
        FilterRegistrationBean<TokenUppercaseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenUppercaseFilter());
        registrationBean.addUrlPatterns("/*"); // Apply to all URL patterns
        return registrationBean;
    }
    
}
