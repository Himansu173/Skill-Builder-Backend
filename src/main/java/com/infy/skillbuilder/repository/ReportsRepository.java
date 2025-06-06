package com.infy.skillbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.entity.Reports;

public interface ReportsRepository extends JpaRepository<Reports, Integer> {
    List<Reports> findByUserIdAndUserTypeOrderByCreatedAtDesc(Integer id, UserType user);
}