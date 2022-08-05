package com.example.usermanagement.service;

import com.example.usermanagement.exception.TicketCollectionException;
import com.example.usermanagement.model.Ticket;
import com.example.usermanagement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ResponseEntity<Ticket> createTicket(Ticket ticket) throws ConstraintViolationException, TicketCollectionException {

        Optional<List<Ticket>> ticketOptional = ticketRepository.findAllByTitle(ticket.getTitle());

        if (ticketOptional.isPresent() && ticketOptional.get().size() > 0) {
            throw new TicketCollectionException(TicketCollectionException.TicketAlreadyExists(ticket.getTitle()));
        } else {
            return ResponseEntity.status(201).body(this.ticketRepository.save(ticket));
        }
    }

    @Override
    public List<Ticket> getAllTickets() {

        List<Ticket> fetchTicket = ticketRepository.findAll();

        if (fetchTicket.size() > 0) {
            return fetchTicket;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public ResponseEntity<Ticket> getTicketByTitle(String title) throws TicketCollectionException {

        Optional<List<Ticket>> ticketWithTitle = this.ticketRepository.findAllByTitle(title);

        if (ticketWithTitle.isPresent() && ticketWithTitle.get().size() > 0) {
            return new ResponseEntity<>(ticketWithTitle.get().get(0), HttpStatus.OK);
        } else {
            throw new TicketCollectionException(TicketCollectionException.TitleNotFoundException(title));
        }
    }

    @Override
    public ResponseEntity<Ticket> getTicketById(String id) throws TicketCollectionException {

        Optional<Ticket> ticket = this.ticketRepository.findById(id);

        if (ticket.isEmpty()) {
            throw new TicketCollectionException(TicketCollectionException.NotFoundException(id));
        } else {
            return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Ticket> updateTicketById(String id, Ticket ticket) throws TicketCollectionException {

        Optional<Ticket> ticketWithId = this.ticketRepository.findById(id);
        Optional<List<Ticket>> ticketWithSameTitle = this.ticketRepository.findAllByTitle(ticket.getTitle());

        if (ticketWithId.isPresent()) {

            if (ticketWithSameTitle.isPresent()) {
                for (Ticket t : ticketWithSameTitle.get()) {
                    if (!t.getId().equals(ticketWithId.get().getId())) {
                        throw new TicketCollectionException(TicketCollectionException.TicketAlreadyExists(ticket.getTitle()));
                    }
                }
            }

            Ticket ticketToUpdate = ticketWithId.get();

            ticketToUpdate.setTitle(ticket.getTitle() != null ? ticket.getTitle() : ticketToUpdate.getTitle());
            ticketToUpdate.setDescription(ticket.getDescription() != null ? ticket.getDescription() : ticketToUpdate.getDescription());
            ticketToUpdate.setEmployees(ticket.getEmployees() != null ? ticket.getEmployees() : ticketToUpdate.getEmployees());
            ticketToUpdate.setType(ticket.getType() != null ? ticket.getType() : ticketToUpdate.getType());
            ticketToUpdate.setStatus(ticket.getStatus() != null ? ticket.getStatus() : ticketToUpdate.getStatus());
            ticketToUpdate.setPriority(ticket.getPriority() != null ? ticket.getPriority() : ticketToUpdate.getPriority());
            ticketRepository.save(ticketToUpdate);

            return new ResponseEntity<>(ticketToUpdate, HttpStatus.OK);
        } else {
            throw new TicketCollectionException(TicketCollectionException.NotFoundException(id));
        }
    }

    @Override
    public ResponseEntity<Ticket> patchTicketById(String id, Ticket ticket) throws TicketCollectionException {

        Optional<Ticket> ticket1 = this.ticketRepository.findById(id);

        if (ticket1.isPresent()) {
            Ticket ticketToPatch = ticket1.get();
            ticketToPatch.setType(ticket.getType() != null ? ticket.getType() : ticketToPatch.getType());
            ticketToPatch.setStatus(ticket.getStatus() != null ? ticket.getStatus() : ticketToPatch.getStatus());
            ticketToPatch.setPriority(ticket.getPriority() != null ? ticket.getPriority() : ticketToPatch.getPriority());
            ticketToPatch.setEmployees(ticket.getEmployees() != null ? ticket.getEmployees() : ticketToPatch.getEmployees());
            ticketRepository.save(ticketToPatch);

            return new ResponseEntity<>(ticketToPatch, HttpStatus.OK);
        } else {
            throw new TicketCollectionException(TicketCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteTicketById(String id) throws TicketCollectionException {

        Optional<Ticket> ticketToDelete = ticketRepository.findById(id);

        if (ticketToDelete.isEmpty()) {
            throw new TicketCollectionException(TicketCollectionException.NotFoundException(id));
        } else {
            ticketRepository.deleteById(id);
        }
    }
}