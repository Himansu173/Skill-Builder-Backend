package com.infy.skillbuilder.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.infy.skillbuilder.dto.BookingStatus;

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
@Table(name = "mentor_availability")
public class MentorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer availabilityId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isRecurring;
    private String timeZone;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}