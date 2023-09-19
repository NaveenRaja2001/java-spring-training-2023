package com.security.demoJWT.controller;

import com.security.demoJWT.entity.*;
import com.security.demoJWT.repo.*;
import com.security.demoJWT.service.TicketBookingService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/demo-controller")
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


//        Show show1=new Show();
//        Show show2=new Show();
//        Show show3=new Show();
//        Show show4=new Show();
//        show1.setEndTime(LocalTime.parse("10:00:00"));
//        show1.setStartTime(LocalTime.parse("12:15:00"));
//        show2.setEndTime(LocalTime.parse("13:00:00"));
//        show2.setStartTime(LocalTime.parse("15:15:00"));
//        show3.setEndTime(LocalTime.parse("16:00:00"));
//        show3.setStartTime(LocalTime.parse("18:15:00"));
//        show4.setEndTime(LocalTime.parse("19:00:00"));
//        show4.setStartTime(LocalTime.parse("21:15:00"));
//        showRepository.save(show1);
//        showRepository.save(show2);
//        showRepository.save(show3);
//        showRepository.save(show4);


//        Location location=new Location();
//        location.setActiveStatus(true);
//        location.setLocationName("Kochi");
//        location.setLocationType("prime");
//        locationRepository.save(location);

        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/addlinks")
    public ResponseEntity<Link> addLinks() {
        Link link = new Link();
//        link.setDate(LocalDate.parse("2018-12-27"));
        Optional<Show> show = showRepository.findById(4);
        Optional<Movie> movie = movieRepository.findById(6);
        Optional<Location> location = locationRepository.findById(7);
        link.setMovie(movie.get());
        link.setShow(show.get());
        link.setLocation(location.get());
        link.setDate(LocalDate.parse("2018-12-27"));
        linkRepository.save(link);
        return ResponseEntity.ok(link);
    }

//    @PostMapping("/showAvailabeMovies")
//    public ResponseEntity<Movie> showAvailabeMovies(@RequestBody Movie movie){
//
//        Movie newMovie=new Movie();
//        newMovie.setMovieName(movie.getMovieName());
//        newMovie.setDescription(movie.getDescription());
//        movieRepository.save(newMovie);
//        return ResponseEntity.ok(newMovie);
//    }




}
