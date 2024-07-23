package com.rest.project.SpringRestProject.exceptions;

public class SensorNameDuplicateException extends RuntimeException{
    public SensorNameDuplicateException(String message) {
        super(message);
    }
}
