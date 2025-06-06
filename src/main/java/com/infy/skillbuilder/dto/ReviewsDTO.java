package com.infy.skillbuilder.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewsDTO {
    private Integer reviewId;
    private Integer mentorId;
    private Integer menteeId;
    @NotNull(message = "{review.rating.notpresent}")
    private Integer rating;
    @NotNull(message = "{review.reviewdescription.notpresent}")
    private String reviewDescription;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime responseCreatedAt;
}