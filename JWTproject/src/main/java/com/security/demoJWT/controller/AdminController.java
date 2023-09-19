package com.security.demoJWT.controller;

import com.security.demoJWT.entity.Location;
import com.security.demoJWT.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    TicketBookingService ticketBookingService;
@GetMapping
    public String hello(){ return "Hi this is admin";}

    @PostMapping("/location")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        Location theLocation = ticketBookingService.addLocation(location);
        return ResponseEntity.ok(theLocation);
    }


    @DeleteMapping("/location")
    public ResponseEntity<Location> removeLocation(@RequestParam String name) {
        Location theLocation = ticketBookingService.removeLocation(name);
        return ResponseEntity.ok(theLocation);
    }

}
