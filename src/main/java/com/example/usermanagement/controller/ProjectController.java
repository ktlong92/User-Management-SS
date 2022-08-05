package com.example.usermanagement.controller;

import com.example.usermanagement.exception.ProjectCollectionException;
import com.example.usermanagement.model.Project;
import com.example.usermanagement.service.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {

    private final ProjectServiceImpl projectServiceImpl;

    @Autowired
    public ProjectController(ProjectServiceImpl projectServiceImpl) {
        this.projectServiceImpl = projectServiceImpl;
    }

    @GetMapping
    public List<Project> fetchAllProjects() {
        return projectServiceImpl.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchProjectById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(projectServiceImpl.getProjectById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> fetchProjectByName(@RequestParam String title) {
        try {
            return new ResponseEntity<>(projectServiceImpl.getProjectByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> registerNewProject(@RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectServiceImpl.createProject(project), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProjectById(@PathVariable String id, @RequestBody Project project) {
        try {
            return ResponseEntity.status(200).body(projectServiceImpl.updateProjectById(id, project));
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (ProjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProjectById(@PathVariable String id, @RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectServiceImpl.patchProjectById(id, project), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String id) {
        try {
            projectServiceImpl.deleteProjectById(id);
            return new ResponseEntity<>("Successfully deleted project with id: " +id, HttpStatus.OK);
        } catch (ProjectCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}