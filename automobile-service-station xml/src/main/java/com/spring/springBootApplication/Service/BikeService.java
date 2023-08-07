package com.spring.springBootApplication.Service;

import org.springframework.stereotype.Component;

@Component("bikeService")
public class BikeService implements ServiceStation {
    BikeService() {
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
