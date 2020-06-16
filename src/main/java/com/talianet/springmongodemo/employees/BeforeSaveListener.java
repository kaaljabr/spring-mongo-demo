package com.talianet.springmongodemo.employees;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

import java.util.UUID;

@Configuration
public class BeforeSaveListener extends AbstractMongoEventListener<Employee> {
    @Override
    public void onBeforeSave(BeforeSaveEvent<Employee> event) {
        Employee employee = event.getSource();
        employee.setId("idhsf");
    }
}
