package com.example.usermanagement.exception;

import java.io.Serial;

public class EmployeeCollectionException extends Exception {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public EmployeeCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Employee with id: " + id + " not found!";
    }

    public static String EmailNotFoundException(String email) {
        return "Employee with email: " + email + " not found!";
    }

    public static String EmployeeAlreadyExists(String email) {
        return "Employee with email: " + email + " already exists";
    }
}

