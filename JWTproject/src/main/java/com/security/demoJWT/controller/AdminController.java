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

/**
 * All these endpoints are accessible one by admin
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    TicketBookingService ticketBookingService;

    @GetMapping
    public String hello() {
        return "Hi this is admin";
    }

    /**
     * This endpoint is used to add the location
     *
     * @param location
     * @return
     */
    @PostMapping("/location")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        Location theLocation = ticketBookingService.addLocation(location);
        return ResponseEntity.ok(theLocation);
    }

    /**
     * This endpoint is used to remove the location
     *
     * @param name
     * @return
     */
    @DeleteMapping("/location")
    public ResponseEntity<Location> removeLocation(@RequestParam String name) {
        Location theLocation = ticketBookingService.removeLocation(name);
        return ResponseEntity.ok(theLocation);
    }

    /**
     * This endpoint is used to add new theatre
     *
     * @param theatreName
     * @param locationName
     * @return
     */
    @PostMapping("/theatre")
    public ResponseEntity<Theatre> addTheatre(@RequestParam String theatreName, @RequestParam String locationName) {
        Theatre addedTheatre = ticketBookingService.addTheatre(theatreName, locationName);
        return ResponseEntity.ok(addedTheatre);
    }

    /**
     * This endpoint is used to remove the existing theatre
     *
     * @param id
     * @return
     */
    @DeleteMapping("/theatre")
    public ResponseEntity<Theatre> removeTheatre(@RequestParam Integer id) {
        Theatre addedTheatre = ticketBookingService.removeTheatre(id);
        return ResponseEntity.ok(addedTheatre);
    }
}

