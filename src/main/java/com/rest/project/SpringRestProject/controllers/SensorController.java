package com.rest.project.SpringRestProject.controllers;


import com.rest.project.SpringRestProject.dto.SensorDTO;
import com.rest.project.SpringRestProject.services.SensorService;
import com.rest.project.SpringRestProject.util.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {

        sensorValidator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError fieldError : errors) {
                errorMsg.append(fieldError.getField())
                        .append("-").append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new NotCreatedException(errorMsg.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Sensor with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNameDuplicateException e) {
        ErrorResponse response = new ErrorResponse(
                "Sensor with this name already exists",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    private com.rest.project.SpringRestProject.models.Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, com.rest.project.SpringRestProject.models.Sensor.class);
    }


    private SensorDTO convertToSensorDTO(com.rest.project.SpringRestProject.models.Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
