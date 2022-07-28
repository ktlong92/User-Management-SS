package com.example.usermanagement.controller;

import com.example.usermanagement.exception.TicketCollectionException;
import com.example.usermanagement.model.Ticket;
import com.example.usermanagement.service.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {

    private final TicketServiceImpl ticketServiceImpl;

    @Autowired
    public TicketController(TicketServiceImpl ticketServiceImpl) {
        this.ticketServiceImpl = ticketServiceImpl;
    }

    @GetMapping
    public List<Ticket> fetchAllTickets() {
        return ticketServiceImpl.getAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchTicketById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(ticketServiceImpl.getTicketById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> fetchTicketByTitle(@RequestParam String title) {
        try {
            return new ResponseEntity<>(ticketServiceImpl.getTicketByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registerTicket(@RequestBody Ticket ticket) {
        try {
            return new ResponseEntity<>(ticketServiceImpl.createTicket(ticket), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putTicketById(@PathVariable String id, @RequestBody Ticket ticket) {
        try {
            return ResponseEntity.status(200).body(ticketServiceImpl.updateTicketById(id, ticket));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (TicketCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchTicketById(@PathVariable String id, @RequestBody Ticket ticket) {
        try {
            return new ResponseEntity<>(ticketServiceImpl.patchTicketById(id, ticket), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable String id) {
        try {
            ticketServiceImpl.deleteTicketById(id);
            return new ResponseEntity<>("Successfully deleted ticket with id: " +id, HttpStatus.OK);
        } catch (TicketCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

