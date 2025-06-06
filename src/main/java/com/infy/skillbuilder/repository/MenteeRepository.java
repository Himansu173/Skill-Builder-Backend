package com.infy.skillbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infy.skillbuilder.entity.Mentee;

public interface MenteeRepository extends JpaRepository<Mentee, Integer> {
    Mentee findByEmail(String email);

    @Query("SELECT m.name FROM Mentee m WHERE m.menteeId=:id")
    String getName(@Param("id") Integer id);
}