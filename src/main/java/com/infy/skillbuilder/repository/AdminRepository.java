package com.infy.skillbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infy.skillbuilder.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);

    @Query("SELECT a.name FROM Admin a WHERE a.adminId=:id")
    String getName(@Param("id") Integer id);
}