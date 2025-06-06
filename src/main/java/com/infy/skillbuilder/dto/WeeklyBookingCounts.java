package com.infy.skillbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeeklyBookingCounts {
    private Long year;
    private Long weekNo;
    private Long noOfBookings;
}