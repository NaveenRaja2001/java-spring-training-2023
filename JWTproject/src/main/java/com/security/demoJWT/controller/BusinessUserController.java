package com.security.demoJWT.controller;

import com.security.demoJWT.entity.*;
import com.security.demoJWT.exception.TicketBookingException;
import com.security.demoJWT.repo.LinkRepository;
import com.security.demoJWT.repo.LocationRepository;
import com.security.demoJWT.repo.MovieRepository;
import com.security.demoJWT.repo.ShowRepository;
import com.security.demoJWT.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/businessUser")
public class BusinessUserController {
    @Autowired
    ShowRepository showRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    LinkRepository linkRepository;
    @Autowired
    TicketBookingService ticketBookingService;
    @PatchMapping("/ticket")
    public ResponseEntity<Booked> cancelTicket(@RequestParam Integer id) {
        Booked cancelTicket = ticketBookingService.cancelTicket(id);
        return ResponseEntity.ok(cancelTicket);
    }

    @PatchMapping("/approveCancellation")
    public ResponseEntity<Booked> approveCancellation(@RequestParam Integer id){
        Booked cancelTicket = ticketBookingService.approveCancellation(id);
        return ResponseEntity.ok(cancelTicket);
    }

    @GetMapping("/addShows")
    public ResponseEntity<Link> addShows(@RequestParam Integer showId,@RequestParam Integer movieId,@RequestParam Integer locationId) {

        Link link=ticketBookingService.addShows(showId,movieId,locationId);
        return ResponseEntity.ok(link);
    }

}
