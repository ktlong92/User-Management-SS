package com.example.usermanagement.repository;

import com.example.usermanagement.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<List<Employee>> findAllEmployeesByEmail(String email);
}
