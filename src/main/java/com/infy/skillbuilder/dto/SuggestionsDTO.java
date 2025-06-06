package com.infy.skillbuilder.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SuggestionsDTO {
    private Integer suggestionId;
    @NotNull(message = "{suggestion.user.notpresent}")
    private Integer userId;
    @NotNull(message = "{suggestion.usertype.notpresent}")
    private UserType userType;
    @NotNull(message = "{suggestion.suggestionstype.notpresent}")
    private SuggestionsType suggestionsType;
    @NotNull(message = "{suggestion.description.notpresent}")
    private String description;
    private ReportAndSuggestionStatus status;
    private String note;
    private LocalDateTime createdAt;
}