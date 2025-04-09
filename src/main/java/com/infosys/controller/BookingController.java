package com.infosys.controller;

import com.infosys.entity.BookFlight;
import com.infosys.entity.Flight;
import com.infosys.entity.Passenger;
import com.infosys.exception.ResourceNotFoundException;
import com.infosys.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @GetMapping("/allBookings/{userName}")
    public List<BookFlight> getAllBookings(@PathVariable String userName){
        List<BookFlight> result=bookingRepository.findByUserName(userName);
    return result;

    }

    @GetMapping("/booking/{bookingId}")
    public BookFlight getBookingByFlightNumber(@PathVariable long bookingId){
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No record found with booking id : " + bookingId));
    }

    @PostMapping("bookFlight/{flightNumber}")
    public ResponseEntity bookFlight(@RequestBody BookFlight bookFlight,@PathVariable String flightNumber) {
        Flight flight=new Flight();
        flight.setFlightNumber(flightNumber);
        bookFlight.setFlight(flight);
        bookFlight.getPassengers().get(0).setBookFlight(bookFlight);
        //bookFlight.getPassengers().get(1).setBookFlight(bookFlight);


        bookFlight.getPassengers().stream().forEach(p->p.setBookFlight(bookFlight));
        BookFlight addedFlight=bookingRepository.save(bookFlight);
        return ResponseEntity.created(URI.create("/"+addedFlight.getBookingId())).body(addedFlight.getBookingId());
    }

    @DeleteMapping("cancelBooking/{bookingId}")
    public ResponseEntity cancelBooking(@PathVariable long bookingId){
        BookFlight existingBooking=bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No record found with booking id : " + bookingId));
        bookingRepository.delete(existingBooking);
        return ResponseEntity.ok().body("your booking has been cancelled successfully");
    }

}
