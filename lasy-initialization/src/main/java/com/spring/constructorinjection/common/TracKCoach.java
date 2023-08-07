package com.spring.constructorinjection.common;

import org.springframework.stereotype.Component;

@Component
public class TracKCoach implements Coach{
    TracKCoach(){
        System.out.println("Contructor :"+getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Track coack .........*";
    }
}
