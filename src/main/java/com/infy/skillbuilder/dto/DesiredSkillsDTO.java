package com.infy.skillbuilder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DesiredSkillsDTO {
    private Integer desiredSkillId;
    @NotNull(message = "{skill.notpresent}")
    private SkillsDTO skill;
}