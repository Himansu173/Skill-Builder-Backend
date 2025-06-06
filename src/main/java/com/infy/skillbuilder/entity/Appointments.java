package com.infy.skillbuilder.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.infy.skillbuilder.dto.RejectionReasons;
import com.infy.skillbuilder.dto.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "appointments")
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;
    private String agenda;
    private Integer duration;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime createdAt;
    private String noteByMentee;
    private Integer rescheduledId;
    @Enumerated(EnumType.STRING)
    private RejectionReasons reasonByMentorIfRejecting;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id", unique = true)
    private Reviews review;
}