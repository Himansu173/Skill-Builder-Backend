package com.infy.skillbuilder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavouriteMentorsDTO {
    private Integer favouriteId;
    @NotNull(message = "{favouritementor.notpresent}")
    private MentorDTO mentor;
}