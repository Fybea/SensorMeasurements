package com.rest.project.SpringRestProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Table(name = "sensor")
@Entity
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurements> measurements;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Measurements> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurements> measurements) {
        this.measurements = measurements;
    }
}
