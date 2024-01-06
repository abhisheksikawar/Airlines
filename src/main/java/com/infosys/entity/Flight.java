package com.infosys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "flights_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @Id
    @Column(name="flight_number")
    private String flightNumber;

    @Column(name="service_provider")
    private String serviceProvider;

    @Column(name="source_airport")
    private String sourceAirport;

    @Column(name="destination_airport")
    private String destinationAirport;

    @Column(name="flight_type")
    private String flightType;

    @Column(name="source_city")
    private String sourceCity;

    @Column(name="destination_city")
    private String destinationCity;

    @Column(name="journey_time")
    private String journeyTime;

    @Column(name="departure_time")
    private String departureTime;

    @Column(name="departure_date")
    private Date departureDate;

    @Column(name="fare")
    private int fare;

    @JsonIgnore
    @OneToOne(mappedBy = "flight")
    private BookFlight bookFlight;




}
