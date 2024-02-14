package com.rest.project.SpringRestProject.controllers;

import com.rest.project.SpringRestProject.Xcharts.Chart;
import com.rest.project.SpringRestProject.dto.MeasurementsDTO;
import com.rest.project.SpringRestProject.models.Measurements;
import com.rest.project.SpringRestProject.services.MeasurementsService;
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
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;

    private final Chart chart;


    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, Chart chart) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.chart = chart;
    }


    @GetMapping()
    public List<MeasurementsDTO> getMeasurements() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementsDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/chart")
    public void getChart() {
        chart.createChart();
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
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementsDTO measurementsDTO,
                                             BindingResult bindingResult) {


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

        measurementsService.save(measurementsDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Measurements with this id wasn't found",
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

    private Measurements convertToMeasurements(MeasurementsDTO measurementsDTO) {
        return modelMapper.map(measurementsDTO, Measurements.class);
    }


    private MeasurementsDTO convertToMeasurementsDTO(Measurements measurements) {
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }

}
