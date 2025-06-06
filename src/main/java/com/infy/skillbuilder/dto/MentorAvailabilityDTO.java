package com.infy.skillbuilder.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MentorAvailabilityDTO {
    private Integer availabilityId;
    @NotNull(message = "{availability.date.notpresent}")
    @FutureOrPresent(message = "{availability.date.invalid}")
    private LocalDate date;
    @NotNull(message = "{availability.time.notpresent}")
    private LocalTime startTime;
    @NotNull(message = "{availability.time.notpresent}")
    private LocalTime endTime;
    @NotNull(message = "{availability.timezone.notpresent}")
    private String timeZone;
    private Boolean isRecurring;
    private BookingStatus status;
}