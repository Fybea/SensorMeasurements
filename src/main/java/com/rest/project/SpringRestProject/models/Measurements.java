package com.rest.project.SpringRestProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

@Table(name = "measurements")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Measurements {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "temperature")
    @NotNull(message = " should not be empty")
    @Min(value = -100, message = " should be greater than -100 C")
    @Max(value = 100, message = " should be less than 100 C")
    private int temperature;

    @Column(name = "raining")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnore
    private Sensor sensor;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
