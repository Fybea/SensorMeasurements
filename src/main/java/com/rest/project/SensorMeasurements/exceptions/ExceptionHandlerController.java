package com.rest.project.SensorMeasurements.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotCreatedException.class)
    public ResponseEntity<Object> handleException(NotCreatedException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ExceptionDetails errorResponse = new ExceptionDetails(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, badRequest);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleException(NotFoundException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ExceptionDetails errorResponse = new ExceptionDetails(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, badRequest);
    }

    @ExceptionHandler(SensorNameDuplicateException.class)
    public ResponseEntity<Object> handleException(SensorNameDuplicateException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ExceptionDetails errorResponse = new ExceptionDetails(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, badRequest);
    }

}
