package com.infosys.controller;

import com.infosys.entity.FlightLookupRequest;
import com.infosys.entity.Flight;
import com.infosys.entity.User;
import com.infosys.exception.ResourceNotFoundException;
import com.infosys.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class FlightController {

    @Autowired
    FlightRepository flightRepository;

    @GetMapping("flight/{flightNumber}")
    public Flight getUserByID(@PathVariable String flightNumber) throws Throwable {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("flight not found with flight number : " + flightNumber));
    }

    @PostMapping("/flightLookup")
    public List<Flight> getFlightInfo(@RequestBody FlightLookupRequest flightLookupRequest){

        return flightRepository.findBySourceCityAndDestinationCity(flightLookupRequest.getSourceCity(),flightLookupRequest.getDestinationCity())
                .orElseThrow(() -> new ResourceNotFoundException("flights not found with given source and destination : " + flightLookupRequest.getSourceCity()+" "+flightLookupRequest.getDestinationCity()));
    }

    @PostMapping("/addFlight")
    public ResponseEntity addFlight(@RequestBody Flight flight) {
        Optional<Flight> existingUser = flightRepository.findByFlightNumber(flight.getFlightNumber());
        if(existingUser.isPresent()){
            return ResponseEntity.badRequest().body("flight number already exists");
        }
        Flight addedFlight=flightRepository.save(flight);
        return ResponseEntity.created(URI.create("/"+flight.getFlightNumber())).build();
    }

    @PutMapping("updateFlight/{flightNumber}")
    public Flight updateFlight(@RequestBody Flight flight, @PathVariable String flightNumber) {

        Flight existingFlight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with flight number : " + flightNumber));

        existingFlight.setFlightType(flight.getFlightType());
        existingFlight.setDepartureDate(flight.getDepartureDate());
        existingFlight.setFare(flight.getFare());
        existingFlight.setDepartureTime(flight.getDepartureTime());
        existingFlight.setDestinationAirport(flight.getDestinationAirport());
        existingFlight.setDestinationCity(flight.getDestinationCity());
        existingFlight.setJourneyTime(flight.getJourneyTime());
        existingFlight.setServiceProvider(flight.getServiceProvider());
        existingFlight.setSourceAirport(flight.getSourceAirport());
        existingFlight.setSourceCity(flight.getSourceCity());

        return flightRepository.save(existingFlight);

    }

    @DeleteMapping("deleteFlight/{flightNumber}")
    public ResponseEntity<Flight> deleteUser(@PathVariable String flightNumber) {
        Flight existingFlight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with flight number : " + flightNumber));

        flightRepository.delete(existingFlight);
        return ResponseEntity.ok(existingFlight);
    }



    }
