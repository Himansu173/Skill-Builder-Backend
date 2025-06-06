package com.infy.skillbuilder.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportsDTO {
    private Integer reportId;
    @NotNull(message = "{report.user.notpresent}")
    private Integer userId;
    @NotNull(message = "{report.usertype.notpresent}")
    private UserType userType;
    @NotNull(message = "{report.violation.notpresent}")
    private CategoryOfViolation categoryOfViolation;
    @NotNull(message = "{report.explanation.notpresent}")
    private String explanation;
    private ReportAndSuggestionStatus status;
    private LocalDateTime createdAt;
}