package com.example.usermanagement.controller;

import com.example.usermanagement.exception.EmployeeCollectionException;
import com.example.usermanagement.model.Employee;
import com.example.usermanagement.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping
    public List<Employee> fetchAllEmployees() {
        return employeeServiceImpl.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchEmployeeById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(employeeServiceImpl.getEmployeeById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> fetchEmployeeByEmail(@RequestParam String email) {
        try {
            return new ResponseEntity<>(employeeServiceImpl.getEmployeeByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registerNewEmployee(@RequestBody Employee employee) {
        try {
            return new ResponseEntity<>(employeeServiceImpl.createEmployee(employee), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putEmployeeById(@PathVariable String id, @RequestBody Employee employee) {
        try {
            return ResponseEntity.status(200).body(employeeServiceImpl.updateEmployeeById(id, employee));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (EmployeeCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchEmployeeById(@PathVariable String id, @RequestBody Employee employee) {
        try {
            return new ResponseEntity<>(employeeServiceImpl.patchEmployeeById(id, employee), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable String id) {
        try {
            employeeServiceImpl.deleteEmployeeById(id);
            return new ResponseEntity<>("Successfully deleted employee with id: " +id, HttpStatus.OK);
        } catch (EmployeeCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}