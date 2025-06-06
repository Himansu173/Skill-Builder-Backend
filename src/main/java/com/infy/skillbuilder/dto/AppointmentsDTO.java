package com.infy.skillbuilder.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentsDTO {
    private Integer appointmentId;
    @NotNull(message = "{appointment.agenda.notpresent}")
    private String agenda;
    @NotNull(message = "{appointment.duration.notpresent}")
    private Integer duration;
    @NotNull(message = "{appointment.date.notpresent}")
    private LocalDate date;
    @NotNull(message = "{appointment.starttime.notpresent}")
    private LocalTime startTime;
    @NotNull(message = "{appointment.endtime.notpresent}")
    private LocalTime endTime;
    private LocalDateTime createdAt;
    private String noteByMentee;
    private Integer rescheduledId;
    private RejectionReasons reasonByMentorIfRejecting;
    private Status status;
    @NotNull(message = "{appointment.mentor.notpresent}")
    private MentorDTO mentor;
    @NotNull(message = "{appointment.mentee.notpresent}")
    private MenteeDTO mentee;
    private ReviewsDTO review;
}