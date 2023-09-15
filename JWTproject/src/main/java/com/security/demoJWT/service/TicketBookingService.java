package com.security.demoJWT.service;

import com.security.demoJWT.entity.Booked;
import com.security.demoJWT.entity.Link;
import com.security.demoJWT.entity.Location;
import com.security.demoJWT.entity.Movie;
import com.security.demoJWT.exception.TicketBookingException;
import com.security.demoJWT.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketBookingService {
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


    public List<Movie> showAvailableMovies() {
        return movieRepository.findAll();
    }


    public List<Link> showAvailableShows() {
        return linkRepository.findAll();
    }

    public Location addLocation(Location location) {
        Optional<Location> newLocation=locationRepository.findBylocationName(location.getLocationName());
        if(newLocation.isPresent()){
            throw new UsernameNotFoundException("No location found");
        }
        locationRepository.save(location);
        return location;
    }

    public Location removeLocation(String name) {
      Location location=locationRepository.findBylocationName(name).orElseThrow(()->new TicketBookingException("Location is not found"));
        locationRepository.delete(location);
        return location;
    }

    public Booked bookTicket(Integer id) {
        Link link=linkRepository.findById(id).orElseThrow(()->new TicketBookingException("Link is not found"));
        Booked booked=new Booked();
        booked.setLink(link);
        booked.setStatus("booked");
        bookedRepository.save(booked);
        return booked;
    }

    public Booked cancelTicket(Integer id) {
        Booked newBooked=bookedRepository.findById(id).orElseThrow(()->new TicketBookingException("ticket is not found"));
        if(newBooked.getStatus().equals("booked")){
            newBooked.setStatus("cancelled");
        }
        bookedRepository.save(newBooked);
        return newBooked;
    }
}
