package com.example.usermanagement.model;

import com.example.usermanagement.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document("api/v1/employees")
public class Employee {
    @Id
    private String id;
    @NotNull(message = "First Name Cannot Be Null")
    private String firstName;
    @NotNull(message = "Last Name Cannot Be Null")
    private String lastName;
    public static String name = (firstName + " " + lastName);
    @NotNull(message = "Phone Number Cannot Be Null")
    private String phoneNumber;
    @NotNull(message = "Email Cannot Be Null")
    private String email;
    @NotNull(message = "Role Cannot Be Null")
    private Role role = Role.Employee;
}

