package com.example.usermanagement.service;

import com.example.usermanagement.exception.ProjectCollectionException;
import com.example.usermanagement.model.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {

    ResponseEntity<Project> createProject (Project project) throws ProjectCollectionException;

    List<Project> getAllProjects();

    ResponseEntity<Project> getProjectByProjectName(String name) throws ProjectCollectionException;

    ResponseEntity<Project> getProjectById(String id) throws ProjectCollectionException;

    ResponseEntity<Project> updateProjectById(String id, Project project) throws ProjectCollectionException;

    ResponseEntity<Project> patchProjectById(String id, Project project) throws ProjectCollectionException;

    void deleteProjectById (String id) throws ProjectCollectionException;
}

