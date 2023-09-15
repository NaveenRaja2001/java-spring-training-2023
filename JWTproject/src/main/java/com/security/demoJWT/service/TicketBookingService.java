package com.security.demoJWT.service;

import com.security.demoJWT.entity.Booked;
import com.security.demoJWT.entity.Link;
import com.security.demoJWT.entity.Location;
import com.security.demoJWT.entity.Movie;
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
        Optional<Location> location=locationRepository.findBylocationName(name);
        locationRepository.delete(location.get());
        return location.get();
    }

    public Booked bookTicket(Integer id) {
        Optional<Link> link=linkRepository.findById(id);
        Booked booked=new Booked();
        booked.setLink(link.get());
        booked.setStatus("booked");
        bookedRepository.save(booked);
        return booked;
    }

    public Booked cancelTicket(Integer id) {
        Optional<Booked> newBooked=bookedRepository.findById(id);
        if(newBooked.get().getStatus().equals("booked")){
            newBooked.get().setStatus("cancelled");
        }
        bookedRepository.save(newBooked.get());
        return newBooked.get();
    }
}
