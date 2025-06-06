package com.infy.skillbuilder.dto;

import java.util.List;

import com.infy.skillbuilder.entity.Reviews;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MenteeDTO {
    private Integer menteeId;
    @NotNull(message = "{user.name.notpresent}")
    @Pattern(regexp = "[A-Z][a-z]+( [A-Z][a-z]*)*", message = "{user.name.invalid}")
    private String name;
    @NotNull(message = "{user.age.notpresent}")
    @Max(value = 80, message = "{user.age.invalid}")
    @Min(value = 10, message = "{user.age.invalid}")
    private Integer age;
    @NotNull(message = "{user.email.notpresent}")
    @Pattern(regexp = "[A-Za-z].*@[A-Za-z]{4,}.[A-Za-z]{2,}", message = "{user.email.invalid}")
    private String email;
    private String profilePic;
    private String careerObjective;
    @NotNull(message = "{user.password.notpresent}")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[\\d])(?=.*[^A-Za-z\\d])([A-Za-z\\d[^A-Za-z\\d]]{8,})", message = "{user.password.invalid}")
    private String password;
    private List<DesiredSkillsDTO> desiredSkills;
    private List<MenteeIndustryInterestsDTO> industryInterests;
    private List<FavouriteMentorsDTO> favouriteMentors;
    private List<Reviews> reviews;
}