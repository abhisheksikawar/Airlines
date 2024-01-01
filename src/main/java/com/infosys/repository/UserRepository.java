package com.infosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosys.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from airline_users where user_name=?1",nativeQuery = true)
    Optional<User> findByUserName(String userName);
}
