package com.spring.constructorinjection.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.constructorinjection.common.Coach;

@RestController
public class DemoController {
private Coach myCoach;
@Autowired
    DemoController(@Qualifier("baseballCoach") Coach myCoach) {
        System.out.println("cONST"+getClass().getSimpleName());
    this.myCoach = myCoach;
    }


//public DemoController(Coach theCoach){
//   myCoach=theCoach;
//}

@GetMapping("/dailyWorkout")
    public String getDailyWorkout(){
    return myCoach.getDailyWorkout();
}

}
