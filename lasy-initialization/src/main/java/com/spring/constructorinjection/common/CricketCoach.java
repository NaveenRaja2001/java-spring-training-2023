package com.spring.constructorinjection.common;

import org.springframework.stereotype.Component;
import com.spring.constructorinjection.common.Coach;

@Component
public class CricketCoach implements Coach {
    CricketCoach(){
        System.out.println("Contructor :"+getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Practice!!!rgfgsl!";
    }
}
