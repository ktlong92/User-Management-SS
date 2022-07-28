package com.example.usermanagement.service;

import com.example.usermanagement.exception.EmployeeCollectionException;
import com.example.usermanagement.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<Employee> createEmployee(Employee employee) throws EmployeeCollectionException;

    List<Employee> getAllEmployees();

    ResponseEntity<Employee> getEmployeeByEmail(String email) throws EmployeeCollectionException;

    ResponseEntity<Employee> getEmployeeById(String id) throws EmployeeCollectionException;

    ResponseEntity<Employee> updateEmployeeById(String id, Employee employee) throws EmployeeCollectionException;

    ResponseEntity<Employee> patchEmployeeById(String id, Employee employee) throws EmployeeCollectionException;

    void deleteEmployeeById(String id) throws EmployeeCollectionException;

}

