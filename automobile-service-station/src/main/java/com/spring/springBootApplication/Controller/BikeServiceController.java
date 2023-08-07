package com.spring.springBootApplication.Controller;


import com.spring.springBootApplication.Service.ServiceStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bikeservice")
public class BikeServiceController {
    ServiceStation bikeService;

    @Autowired
    public BikeServiceController(@Qualifier("bikeService") ServiceStation bikeService) {
        System.out.println("Constructor :" + getClass().getSimpleName());
        this.bikeService = bikeService;
    }

    @GetMapping("/majorservice")
    public String bikeMajorService() {
        return bikeService.majorService();
    }

    @GetMapping("/minorservice")
    public String bikeMinorService() {
        return bikeService.minorService();
    }

}

