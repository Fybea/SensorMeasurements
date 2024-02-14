package com.rest.project.SpringRestProject.util;

import com.rest.project.SpringRestProject.dto.SensorDTO;
import com.rest.project.SpringRestProject.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        if(sensorService.findOne(sensorDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name is already exists");
        }
    }
}
