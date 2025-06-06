package com.infy.skillbuilder.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
public class WorkExperiencesDTO {
    private Integer workId;
    @NotNull(message = "{work.name.notpresent}")
    private String companyName;
    @NotNull(message = "{work.role.notpresent}")
    private String role;
    @NotNull(message = "{work.startdate.notpresent}")
    @PastOrPresent(message = "{work.startdate.invaid}")
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull(message = "{work.roledescription.notpresent}")
    private String roleDescription;
}