package com.spring.constructorinjection.common;

import org.springframework.stereotype.Component;

@Component
public class BaseballCoach implements Coach {
    BaseballCoach(){
        System.out.println("Constructor :"+getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Hai this is Baseball";
    }
}
