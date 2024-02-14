package com.rest.project.SpringRestProject.repositories;


import com.rest.project.SpringRestProject.models.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {
    int countByRaining(boolean raining);
}
