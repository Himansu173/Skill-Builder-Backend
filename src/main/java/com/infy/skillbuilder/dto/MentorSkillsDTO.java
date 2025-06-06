package com.infy.skillbuilder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MentorSkillsDTO {
    private Integer mentorSkillId;
    @NotNull(message = "{skill.notpresent}")
    private SkillsDTO skill;
    private SkillLevel skillLevel;
}