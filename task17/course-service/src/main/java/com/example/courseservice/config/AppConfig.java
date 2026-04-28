package com.example.courseservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * RestTemplate bean used by CourseService to call Student Service REST API.
     * Registered as a Spring bean so it can be @Autowired anywhere.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
