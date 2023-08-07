package com.spring.springBootApplication.Controller;

import com.spring.springBootApplication.Service.ServiceStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carservice")
public class CarServiceController {
    ServiceStation carService;

    @Autowired
    public void setCarService(@Qualifier("carService") ServiceStation carService) {
        this.carService = carService;
    }

    @GetMapping("/majorservice")
    public String carMajorService() {

        return carService.majorService();
    }

    @GetMapping("/minorservice")
    public String carMinorService() {
        return carService.minorService();
    }
}
