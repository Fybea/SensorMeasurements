package com.rest.project.SpringRestProject.repositories;

import com.rest.project.SpringRestProject.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findByName(String name);


}
