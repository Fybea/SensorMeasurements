package com.rest.project.SensorMeasurements.exceptions;

public class SensorNameDuplicateException extends RuntimeException{
    public SensorNameDuplicateException(String message) {
        super(message);
    }
}
