package com.spring.springBootApplication;

import com.spring.springBootApplication.Controller.BikeServiceController;
import com.spring.springBootApplication.Controller.CarServiceController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication

public class AutomobileServiceStation {

    public static void main(String[] args) {
        SpringApplication.run(AutomobileServiceStation.class, args);
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("beans.xml");
        applicationContext.getBean("bikeServiceController", BikeServiceController.class);
        applicationContext.getBean("carServiceController", CarServiceController.class);
    }

}
