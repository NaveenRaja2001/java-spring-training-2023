package com.security.demoJWT.service;

import com.security.demoJWT.entity.*;
import com.security.demoJWT.exception.TicketBookingException;
import com.security.demoJWT.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Optional<Location> newLocation = locationRepository.findBylocationName(location.getLocationName());
        if (newLocation.isPresent()) {
            throw new TicketBookingException(" location is already found");
        }
        locationRepository.save(location);
        return location;
    }

    public Location removeLocation(String name) {
        Location location = locationRepository.findBylocationName(name).orElseThrow(() -> new TicketBookingException("Location is not found"));
        locationRepository.delete(location);
        return location;
    }

    public Booked bookTicket(Integer id,int ticketCount) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new TicketBookingException("Link is not found"));
        Location location=link.getLocation();
        int availableCount=location.getAvailableCount();
        if(!(availableCount>ticketCount)){
            throw new TicketBookingException("Ticket is not available or ticket count may ne less than expected");
        }
        location.setAvailableCount(availableCount-ticketCount);
        locationRepository.save(location);
        Booked booked = new Booked();
        booked.setLink(link);
        booked.setStatus("booked");
        booked.setTicketCount(ticketCount);
        bookedRepository.save(booked);
        return booked;
    }

    public Booked cancelTicket(Integer id) {
        Booked newBooked = bookedRepository.findById(id).orElseThrow(() -> new TicketBookingException("ticket is not found"));
        if (newBooked.getStatus().equals("booked")||newBooked.getStatus().equals("requestForCancel")) {
            newBooked.setStatus("cancelled");
        }
        else {
            throw new TicketBookingException("Booked Ticket Id not found");
        }
        Location location=locationRepository.findById(newBooked.getLink().getLocation().getId()).orElseThrow();
        location.setAvailableCount(location.getAvailableCount()+newBooked.getTicketCount());
        bookedRepository.save(newBooked);
        return newBooked;
    }

    public Booked requestForCancellation(Integer id) {
        Booked newBooked = bookedRepository.findById(id).orElseThrow(() -> new TicketBookingException("ticket is not found"));
        if (newBooked.getStatus().equals("booked")) {
            newBooked.setStatus("requestForCancel");
        }
        else {
            throw new TicketBookingException("Booked Ticket Id not found");
        }
        bookedRepository.save(newBooked);
        return newBooked;
    }

    public Booked approveCancellation(Integer id) {
        Booked newBooked = bookedRepository.findById(id).orElseThrow(() -> new TicketBookingException("ticket is not found"));
        if (newBooked.getStatus().equals("requestForCancel")) {
            newBooked.setStatus("cancelled");
        }
        else {
            throw new TicketBookingException("Booked Ticket Id not found");
        }
        Location location=locationRepository.findById(newBooked.getLink().getLocation().getId()).orElseThrow();
        location.setAvailableCount(location.getAvailableCount()+newBooked.getTicketCount());
        bookedRepository.save(newBooked);
        return newBooked;
    }


    public Link addShows(Integer showId, Integer movieId, Integer locationId) {
        Link link = new Link();
        Show show = showRepository.findById(showId).orElseThrow(()->new TicketBookingException("Show Id not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new TicketBookingException("Movie Id Not Found"));
        Location location = locationRepository.findById(locationId).orElseThrow(()->new TicketBookingException("LocationId Not Found"));
        link.setMovie(movie);
        link.setShow(show);
        link.setLocation(location);
        link.setDate(LocalDate.parse("2018-12-28"));
        linkRepository.save(link);
        return link;
    }
}
