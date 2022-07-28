package com.example.usermanagement.service;

import com.example.usermanagement.exception.TicketCollectionException;
import com.example.usermanagement.model.Ticket;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {

    ResponseEntity<Ticket> createTicket (Ticket ticket) throws TicketCollectionException;

    List<Ticket> getAllTickets();

    ResponseEntity<Ticket> getTicketByTitle(String title) throws TicketCollectionException;

    ResponseEntity<Ticket> getTicketById(String id) throws TicketCollectionException;

    ResponseEntity<Ticket> updateTicketById(String id, Ticket ticket) throws TicketCollectionException;

    ResponseEntity<Ticket> patchTicketById(String id, Ticket ticket) throws TicketCollectionException;

    void deleteTicketById (String id) throws TicketCollectionException;
}