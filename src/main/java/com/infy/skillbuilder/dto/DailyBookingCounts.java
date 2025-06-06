package com.infy.skillbuilder.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyBookingCounts {
    private LocalDate date;
    private Long noOfBookings;
}