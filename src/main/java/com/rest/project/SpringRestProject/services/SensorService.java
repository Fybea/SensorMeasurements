package com.rest.project.SpringRestProject.services;

import com.rest.project.SpringRestProject.models.Sensor;
import com.rest.project.SpringRestProject.repositories.SensorRepository;
import com.rest.project.SpringRestProject.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }


    public Optional<Sensor> findOne(String name) {
        return sensorRepository.findByName(name);
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        return foundSensor.orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
