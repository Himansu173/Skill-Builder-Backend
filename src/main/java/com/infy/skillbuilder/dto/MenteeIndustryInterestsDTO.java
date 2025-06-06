package com.infy.skillbuilder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenteeIndustryInterestsDTO {
    private Integer menteeIndustryInterestsId;
    @NotNull(message="{industryinterests.notpresent}")
    private SpecializationsDTO industryInterests;
}