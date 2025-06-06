package com.infy.skillbuilder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkillsDTO {
    private Integer skillId;
    @NotNull(message = "{skill.skilname.notpresent}")
    private String skillName;
}