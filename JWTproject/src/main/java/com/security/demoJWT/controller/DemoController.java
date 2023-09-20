package com.security.demoJWT.controller;

import com.security.demoJWT.entity.Booked;
import com.security.demoJWT.repo.BookedRepository;
import com.security.demoJWT.repo.LinkRepository;
import com.security.demoJWT.repo.LocationRepository;
import com.security.demoJWT.repo.MovieRepository;
import com.security.demoJWT.repo.ShowRepository;
import com.security.demoJWT.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class DemoController {
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    BookedRepository bookedRepository;

    @Autowired
    TicketBookingService ticketBookingService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @PatchMapping("/ticket")
    public ResponseEntity<Booked> requestForCancellation(@RequestParam Integer id) {
        Booked cancelTicket = ticketBookingService.requestForCancellation(id);
        return ResponseEntity.ok(cancelTicket);
    }
}
