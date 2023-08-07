package com.spring.constructorinjection.common;

import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach{
    TennisCoach(){
        System.out.println("Constructor :"+getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Hi THIS TENNIS";
    }
}
