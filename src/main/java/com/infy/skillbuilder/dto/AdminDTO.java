package com.infy.skillbuilder.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AdminDTO {
    private Integer adminId;
    @NotNull(message = "{user.name.notpresent}")
    @Pattern(regexp = "[A-Z][a-z]+( [A-Z][a-z]*)*", message = "{user.name.invalid}")
    private String name;
    @NotNull(message = "{user.email.notpresent}")
    @Pattern(regexp = "[A-Za-z].*@[A-Za-z]{4,}.[A-Za-z]{2,}", message = "{user.email.invalid}")
    private String email;
    private String profilePic;
    private String password;
}