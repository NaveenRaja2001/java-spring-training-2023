package com.security.demoJWT.controller;

import com.security.demoJWT.entity.Location;
import com.security.demoJWT.entity.Theatre;
import com.security.demoJWT.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    TicketBookingService ticketBookingService;

    @GetMapping
    public String hello() {
        return "Hi this is admin";
    }

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

    @PostMapping("/theatre")
    public ResponseEntity<Theatre> addTheatre(@RequestParam String theatreName ,@RequestParam String locationName) {
        Theatre addedTheatre = ticketBookingService.addTheatre(theatreName,locationName);
        return ResponseEntity.ok(addedTheatre);
    }

    @DeleteMapping("/theatre")
    public ResponseEntity<Theatre> removeTheatre(@RequestParam Integer id) {
        Theatre addedTheatre = ticketBookingService.removeTheatre(id);
        return ResponseEntity.ok(addedTheatre);
    }
}

