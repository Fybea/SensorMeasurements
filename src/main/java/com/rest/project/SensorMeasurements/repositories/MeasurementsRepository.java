package com.rest.project.SensorMeasurements.repositories;


import com.rest.project.SensorMeasurements.models.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
    int countByRaining(boolean raining);
}
