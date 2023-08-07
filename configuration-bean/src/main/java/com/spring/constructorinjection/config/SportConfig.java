package com.spring.constructorinjection.config;

import com.spring.constructorinjection.common.Coach;
import com.spring.constructorinjection.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {
    @Bean
    public Coach swimCoach(){
        return new SwimCoach();
    }

}
