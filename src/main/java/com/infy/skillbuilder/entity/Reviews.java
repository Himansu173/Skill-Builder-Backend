package com.infy.skillbuilder.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;
    @Column(name = "mentor_id")
    private Integer mentorId;
    @Column(name = "mentee_id")
    private Integer menteeId;
    private Integer rating;
    private String reviewDescription;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime responseCreatedAt;
}