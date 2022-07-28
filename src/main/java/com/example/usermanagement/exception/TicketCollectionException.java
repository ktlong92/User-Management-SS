package com.example.usermanagement.exception;

import java.io.Serial;

public class TicketCollectionException extends Exception {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public TicketCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "Ticket with id: " + id + " not found!";
    }

    public static String TitleNotFoundException(String title) {
        return "Ticket with title: " + title + " not found!";
    }


    public static String TicketAlreadyExists(String title) {
        return "Ticket with title: " + title + " already exists";
    }
}
