package com.infosys.repository;

import com.infosys.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "select * from flights_info where source_city=?1 and destination_city=?2",nativeQuery = true)
    Optional<List<Flight>> findBySourceCityAndDestinationCity(String sourceCity, String destinationCity);

    @Query(value = "select * from flights_info where flight_number=?1",nativeQuery = true)
    Optional<Flight> findByFlightNumber(String flightNumber);
}
