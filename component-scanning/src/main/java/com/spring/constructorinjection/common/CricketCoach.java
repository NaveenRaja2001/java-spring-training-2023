package com.spring.constructorinjection.common;

import jdk.jfr.Label;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.spring.constructorinjection.common.Coach;

@Component
public class CricketCoach implements Coach {
    CricketCoach(){
        System.out.println("Constructor :"+getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Practice!!!rgfgsl!";
    }
}
