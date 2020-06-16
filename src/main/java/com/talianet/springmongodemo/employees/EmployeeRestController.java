package com.talianet.springmongodemo.employees;


import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.talianet.springmongodemo.error.ResponseError;
import lombok.extern.flogger.Flogger;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    @Autowired
    EmployeeRepository employeeRepository;
    
    @GetMapping
    public ResponseEntity<?> listEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
        } catch (Exception exception) {
            ResponseError responseError = new ResponseError("Internal Server Error", exception.getMessage());
            return new ResponseEntity<ResponseError>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{employeeId}")
    public ResponseEntity<?> listEmployees(@PathVariable Long employeeId) {
        Employee employee;
        try {
            employee = employeeRepository.findByEmployeeId(employeeId);
        } catch (Exception exception) {
            ResponseError responseError = new ResponseError("Internal Server Error", exception.getMessage());
            return new ResponseEntity<ResponseError>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) {
        try {
            Employee employee = employeeRepository.findByEmployeeId(employeeId);
            if (employee == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                employeeRepository.delete(employee);
            }
        } catch (Exception exception) {
            ResponseError responseError = new ResponseError("Internal Server Error", exception.getMessage());
            return new ResponseEntity<ResponseError>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertEmployee(@RequestBody Employee employee) {
        try {
            employeeRepository.insert(employee);
        } catch (ConstraintViolationException | DuplicateKeyException exception) {
            ResponseError responseError = new ResponseError("Invalid Input", exception.getMessage());
            return new ResponseEntity<ResponseError>(responseError, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            ResponseError responseError = new ResponseError("Internal Server Error", exception.getMessage());
            return new ResponseEntity<ResponseError>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
