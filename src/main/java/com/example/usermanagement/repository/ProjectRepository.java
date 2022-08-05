package com.example.usermanagement.repository;

import com.example.usermanagement.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    Optional<List<Project>> findAllByTitle(String name);
}
