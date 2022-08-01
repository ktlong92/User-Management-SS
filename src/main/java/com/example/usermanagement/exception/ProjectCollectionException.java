package com.example.usermanagement.exception;

import java.io.Serial;

public class ProjectCollectionException extends Exception {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public ProjectCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Project with id: " + id + " not found!";
    }

    public static String NameNotFoundException(String name) {
        return "Project with name: " + name + " not found!";
    }

    public static String ProjectAlreadyExists(String name) {
        return "Project with name: " + name + " already exists";
    }
}
