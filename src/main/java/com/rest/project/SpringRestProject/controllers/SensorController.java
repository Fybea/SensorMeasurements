package com.rest.project.SpringRestProject.controllers;


import com.rest.project.SpringRestProject.dto.SensorDTO;
import com.rest.project.SpringRestProject.services.SensorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
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
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO) {

        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    private com.rest.project.SpringRestProject.models.Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, com.rest.project.SpringRestProject.models.Sensor.class);
    }


    private SensorDTO convertToSensorDTO(com.rest.project.SpringRestProject.models.Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
