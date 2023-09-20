package com.security.demoJWT.controller;

import com.security.demoJWT.entity.Booked;
import com.security.demoJWT.entity.Link;
import com.security.demoJWT.entity.Movie;
import com.security.demoJWT.repo.BookedRepository;
import com.security.demoJWT.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    TicketBookingService ticketBookingService;

    @Autowired
    BookedRepository bookedRepository;

    @GetMapping
    public String hello() {
        return "Hi this is user";
    }

    @GetMapping("/showAvailableMovies")
    public ResponseEntity<List<Movie>> showAvailableMovies() {
        List<Movie> movies = ticketBookingService.showAvailableMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/showAvailableShows")
    public ResponseEntity<List<Link>> showAvailableShows() {
        List<Link> links = ticketBookingService.showAvailableShows();
        return ResponseEntity.ok(links);
    }

    @PostMapping("/ticket")
    public ResponseEntity<Booked> bookTicket(@RequestParam Integer id,Integer ticketCount) {

        Booked bookedTicket = ticketBookingService.bookTicket(id,ticketCount);
        return ResponseEntity.ok(bookedTicket);
    }

    @GetMapping("/bookedtickets")
    public ResponseEntity<List<Booked>> bookedOrCancelledTickets(@RequestParam String Status) {
        List<Booked> TicketStatus = bookedRepository.findAllByStatus(Status);
        return ResponseEntity.ok(TicketStatus);
    }
}
