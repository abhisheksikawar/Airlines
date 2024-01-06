package com.infosys.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "booking_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long bookingId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "food_services")
    private String foodServices;

    @Column(name="insurance_flg")
    private String insuranceFlg;

    @Column(name="comments")
    private String comments;

    @OneToOne
    @JoinColumn(name = "flight_number")
    private Flight flight;


    @OneToMany(mappedBy = "bookFlight",cascade = CascadeType.ALL)
    private List<Passenger> passengers;





}
