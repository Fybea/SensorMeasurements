package com.rest.project.SensorMeasurements.controllers;

import com.rest.project.SensorMeasurements.dto.MeasurementsDTO;
import com.rest.project.SensorMeasurements.models.Measurements;
import com.rest.project.SensorMeasurements.services.MeasurementsService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;


    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public List<MeasurementsDTO> getMeasurements() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementsDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurementsDTO getMeasurement(@PathVariable("id") int id) {
        return convertToMeasurementsDTO(measurementsService.findOne(id));
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementsService.getRainyDaysCount();
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementsDTO measurementsDTO) {
        measurementsService.save(measurementsDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private MeasurementsDTO convertToMeasurementsDTO(Measurements measurements) {
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }
}
