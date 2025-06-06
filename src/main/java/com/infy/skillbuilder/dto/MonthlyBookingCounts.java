package com.infy.skillbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyBookingCounts {
    private Long year;
    private Long monthNo;
    private Long noOfBookings;
}