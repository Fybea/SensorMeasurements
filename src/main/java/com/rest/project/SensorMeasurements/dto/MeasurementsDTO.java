package com.rest.project.SensorMeasurements.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MeasurementsDTO {

    @Min(value = -100, message = " should be greater than -100 C")
    @Max(value = 100, message = " should be less than 100 C")
    private int temperature;

    private SensorDTO sensor;

    private boolean raining;
}
