package com.rest.project.SpringRestProject.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ExceptionDetails {
    private String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}
