package com.infy.skillbuilder.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "work_experiences")
public class WorkExperiences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workId;
    private String companyName;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String roleDescription;
}