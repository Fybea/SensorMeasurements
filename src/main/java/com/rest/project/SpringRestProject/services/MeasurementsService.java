package com.rest.project.SpringRestProject.services;

import com.rest.project.SpringRestProject.dto.MeasurementsDTO;
import com.rest.project.SpringRestProject.exceptions.NotCreatedException;
import com.rest.project.SpringRestProject.exceptions.NotFoundException;
import com.rest.project.SpringRestProject.models.Measurements;
import com.rest.project.SpringRestProject.repositories.MeasurementsRepository;
import com.rest.project.SpringRestProject.repositories.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        return measurementsRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id " + id));
    }

    public int getRainyDaysCount() {
        return measurementsRepository.countByRaining(true);
    }


    @Transactional
    public void save(MeasurementsDTO measurementsDTO) {

        if (sensorRepository.findByName(measurementsDTO.getSensor().getName()).isPresent()) {
            throw new NotCreatedException("Sensor with this name: "
                    + measurementsDTO.getSensor().getName() +
                    " - already exists.");
        }

        Measurements measurement = new Measurements();
        measurement.setTemperature(measurementsDTO.getTemperature());
        measurement.setRaining(measurementsDTO.isRaining());
        measurement.setCreatedAt(LocalDateTime.now());

        measurementsRepository.save(measurement);
    }

}
