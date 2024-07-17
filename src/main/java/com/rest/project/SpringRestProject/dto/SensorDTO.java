package com.rest.project.SpringRestProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorDTO {

    @NotEmpty(message = " should not be empty")
    @Size(min = 2, max = 50, message = " should be between 2 and 50 characters")
    private String name;
}
