package com.example.usermanagement.repository;

import com.example.usermanagement.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    Optional<List<Ticket>> findAllByTitle(String title);
}