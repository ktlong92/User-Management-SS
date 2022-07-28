package com.example.usermanagement.service;

import com.example.usermanagement.exception.EmployeeCollectionException;
import com.example.usermanagement.model.Employee;
import com.example.usermanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee) throws ConstraintViolationException, EmployeeCollectionException {

        Optional<List<Employee>> employeeOptional = employeeRepository.findAllEmployeesByEmail(employee.getEmail());

        if (employeeOptional.isPresent() && employeeOptional.get().size() > 0) {
            throw new EmployeeCollectionException(EmployeeCollectionException.EmployeeAlreadyExists(employee.getEmail()));
        }else{
            return ResponseEntity.status(201).body(this.employeeRepository.save(employee));
        }
    }

    @Override
    public List<Employee> getAllEmployees() {

        List<Employee> fetchEmployee = employeeRepository.findAll();

        if (fetchEmployee.size() > 0) {
            return fetchEmployee;
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeByEmail(String email) throws EmployeeCollectionException {

        Optional<List<Employee>> employeeWithEmail = this.employeeRepository.findAllEmployeesByEmail(email);

        if (employeeWithEmail.isPresent() && employeeWithEmail.get().size() > 0) {
            return new ResponseEntity<>(employeeWithEmail.get().get(0), HttpStatus.OK);
        } else {
            throw new EmployeeCollectionException(EmployeeCollectionException.EmailNotFoundException(email));
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) throws EmployeeCollectionException {

        Optional<Employee> employee = this.employeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new EmployeeCollectionException(EmployeeCollectionException.NotFoundException(id));
        } else {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Employee> updateEmployeeById(String id, Employee employee) throws EmployeeCollectionException {

        Optional<Employee> employeeWithId = this.employeeRepository.findById(id);
        Optional<List<Employee>> employeeWithSameEmail = this.employeeRepository.findAllEmployeesByEmail(employee.getEmail());

        if(employeeWithId.isPresent()) {

            if (employeeWithSameEmail.isPresent()) {
                for(Employee p : employeeWithSameEmail.get()) {
                    if (!p.getId().equals(employeeWithId.get().getId())) {
                        throw new EmployeeCollectionException(EmployeeCollectionException.EmployeeAlreadyExists(employee.getEmail()));
                    }
                }
            }

            Employee employeeToUpdate = employeeWithId.get();

            employeeToUpdate.setFirstName(employee.getFirstName() != null ? employee.getFirstName() : employeeToUpdate.getFirstName());
            employeeToUpdate.setLastName(employee.getLastName() != null ? employee.getLastName() : employeeToUpdate.getLastName());
            employeeToUpdate.setPhoneNumber(employee.getPhoneNumber() != null ? employee.getPhoneNumber() : employeeToUpdate.getPhoneNumber());
            employeeToUpdate.setEmail(employee.getEmail() != null ? employee.getEmail() : employeeToUpdate.getEmail());
            employeeToUpdate.setRole(employee.getRole() != null ? employee.getRole() : employeeToUpdate.getRole());
            employeeRepository.save(employeeToUpdate);

            return new ResponseEntity<>(employeeToUpdate, HttpStatus.OK);
        } else {
            throw new EmployeeCollectionException(EmployeeCollectionException.NotFoundException(id));
        }
    }

    @Override
    public ResponseEntity<Employee> patchEmployeeById(String id, Employee employee) throws EmployeeCollectionException {

        Optional<Employee> employee1 = this.employeeRepository.findById(id);

        if(employee1.isPresent()) {
            Employee employeeToPatch = employee1.get();
            employeeToPatch.setRole(employee.getRole() != null ? employee.getRole() : employeeToPatch.getRole());
            employeeRepository.save(employeeToPatch);

            return new ResponseEntity<>(employeeToPatch, HttpStatus.OK);
        } else {
            throw new EmployeeCollectionException(EmployeeCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteEmployeeById(String id) throws EmployeeCollectionException {

        Optional<Employee> personnelToDelete = employeeRepository.findById(id);

        if(personnelToDelete.isEmpty()) {
            throw new EmployeeCollectionException(EmployeeCollectionException.NotFoundException(id));
        }else {
            employeeRepository.deleteById(id);
        }
    }
}

