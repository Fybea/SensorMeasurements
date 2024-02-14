package com.rest.project.SpringRestProject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementsDTO {

    @NotNull(message = " should not be empty")
    @Min(value = -100, message = " should be greater than -100 C")
    @Max(value = 100, message = " should be less than 100 C")
    private int temperature;

    private SensorDTO sensor;

    private boolean raining;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
