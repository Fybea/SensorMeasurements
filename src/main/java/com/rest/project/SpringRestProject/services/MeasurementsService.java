package com.rest.project.SpringRestProject.services;

import com.rest.project.SpringRestProject.dto.MeasurementsDTO;
import com.rest.project.SpringRestProject.models.Measurements;
import com.rest.project.SpringRestProject.models.Sensor;
import com.rest.project.SpringRestProject.repositories.MeasurementsRepository;
import com.rest.project.SpringRestProject.repositories.SensorRepository;
import com.rest.project.SpringRestProject.util.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorRepository sensorRepository;

    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorRepository sensorRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }

    public Measurements findOne(int id) {
        Optional<Measurements> foundMeasurement = measurementsRepository.findById(id);
        return foundMeasurement.orElse(null);
    }

    public int getRainyDaysCount() {
        return measurementsRepository.countByRaining(true);
    }


    @Transactional
    public void save(MeasurementsDTO measurementsDTO) {
        Optional<Sensor> sensor = sensorRepository.findByName(measurementsDTO.getSensor().getName());

        Measurements measurement = new Measurements();
        measurement.setTemperature(measurementsDTO.getTemperature());
        measurement.setRaining(measurementsDTO.isRaining());
        measurement.setSensor(sensor.orElseThrow(NotFoundException::new));
        measurement.setCreatedAt(LocalDateTime.now());

        measurementsRepository.save(measurement);
    }

}
