package com.spring.constructorinjection.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.constructorinjection.common.Coach;

@RestController
public class DemoController {
private Coach myCoach;

//    public void setMyCoach(@Qualifier("cricketCoach") Coach myCoach) {
//        this.myCoach = myCoach;
//    }



@Autowired
public DemoController(@Qualifier("swimCoach") Coach theCoach){
    System.out.println("Constructor :"+getClass().getSimpleName());
   myCoach=theCoach;
}

@GetMapping("/dailyWorkout")
    public String getDailyWorkout(){
    return myCoach.getDailyWorkout();
}

}
