package com.security.demoJWT.controller;

import com.security.demoJWT.entity.*;
import com.security.demoJWT.repo.*;
import com.security.demoJWT.service.TicketBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

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

//    @GetMapping("/addlinks")
//    public ResponseEntity<Link> addLinks() {
//        Link link = new Link();
//        Optional<Show> show = showRepository.findById(4);
//        Optional<Movie> movie = movieRepository.findById(6);
//        Optional<Location> location = locationRepository.findById(7);
//        link.setMovie(movie.get());
//        link.setShow(show.get());
//        link.setLocation(location.get());
//        link.setDate(LocalDate.parse("2018-12-27"));
//        linkRepository.save(link);
//        return ResponseEntity.ok(link);
//    }

    @PatchMapping("/ticket")
    public ResponseEntity<Booked> requestForCancellation(@RequestParam Integer id) {
        Booked cancelTicket = ticketBookingService.requestForCancellation(id);
        return ResponseEntity.ok(cancelTicket);
    }
}
