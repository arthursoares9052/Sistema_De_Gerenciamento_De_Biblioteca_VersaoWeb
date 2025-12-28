package com.example.biblioteca.seguranca;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIKeyFilterConfig {

    @Bean
    public FilterRegistrationBean<APIKeyFilter> apiKeyFilter() {
        FilterRegistrationBean<APIKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new APIKeyFilter());
        registrationBean.addUrlPatterns("/api/*"); 
        return registrationBean;
    }
}
