package com.spring.springBootApplication.Service;

import org.springframework.stereotype.Component;

@Component
public class CarService implements ServiceStation {
    CarService() {
        System.out.println("Contructor--->" + getClass().getSimpleName());
    }

    @Override
    public String minorService() {

        return "Minor Service" + getClass().getSimpleName();
    }

    @Override
    public String majorService() {

        return "Major Service" + getClass().getSimpleName();
    }
}
