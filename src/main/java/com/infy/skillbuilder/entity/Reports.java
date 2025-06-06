package com.infy.skillbuilder.entity;

import java.time.LocalDateTime;

import com.infy.skillbuilder.dto.CategoryOfViolation;
import com.infy.skillbuilder.dto.ReportAndSuggestionStatus;
import com.infy.skillbuilder.dto.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reports")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;
    private Integer userId;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private CategoryOfViolation categoryOfViolation;
    private String explanation;
    @Enumerated(EnumType.STRING)
    private ReportAndSuggestionStatus status;
    private LocalDateTime createdAt;
}