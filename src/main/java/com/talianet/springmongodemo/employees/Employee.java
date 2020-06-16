package com.talianet.springmongodemo.employees;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document(collection = "employees")
public @Data class Employee {

    @Id
    private String id;

    @Field("first_name")
    @NotBlank(message = "First name must not be empty")
    private String firstName;

    @Field("last_name")
    @NotBlank(message = "Last name must not be empty")
    private String lastName;

    @Indexed
    @Field("employee_id")
    @NotNull(message = "Employee ID must not be empty")
    private Long employeeId;

    @Indexed
    @Email(message = "Email is not valid", regexp = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+")
    private String email;

    private String phone;

    private double salary;
}