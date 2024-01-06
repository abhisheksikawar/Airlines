package com.infosys.repository;

import com.infosys.entity.BookFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookFlight, Long> {


    List<BookFlight> findByUserName(String userName);
}
