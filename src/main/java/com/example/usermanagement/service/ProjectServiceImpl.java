package com.example.usermanagement.service;

import com.example.usermanagement.exception.ProjectCollectionException;
import com.example.usermanagement.model.Project;
import com.example.usermanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Project> createProject(Project project) throws ConstraintViolationException, ProjectCollectionException {

        Optional<List<Project>> projectOptional = projectRepository.findAllByName(project.getName());

        if (projectOptional.isPresent() && projectOptional.get().size() > 0) {
            throw new ProjectCollectionException(ProjectCollectionException.ProjectAlreadyExists(project.getName()));
        }else{
            return ResponseEntity.status(201).body(this.projectRepository.save(project));
        }
    }

    @Override
    public List<Project> getAllProjects() {

        List<Project> fetchProject = projectRepository.findAll();

        if (fetchProject.size() > 0) {
            return fetchProject;
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public ResponseEntity<Project> getProjectByName(String name) throws  ProjectCollectionException{

        Optional<List<Project>> projectWithName = this.projectRepository.findAllByName(name);

        if (projectWithName.isPresent() && projectWithName.get().size() > 0) {
            return new ResponseEntity<>(projectWithName.get().get(0), HttpStatus.OK);
        } else {
            throw new ProjectCollectionException(ProjectCollectionException.NameNotFoundException(name));
        }
    }

    @Override
    public ResponseEntity<Project> getProjectById(String id) throws ProjectCollectionException {

        Optional<Project> project = this.projectRepository.findById(id);

        if (project.isEmpty()) {
            throw new ProjectCollectionException(ProjectCollectionException.NotFoundException(id));
        } else {
            return new ResponseEntity<>(project.get(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Project> updateProjectById(String id, Project project) throws ProjectCollectionException {

        Optional<Project> projectWithId = this.projectRepository.findById(id);
        Optional<List<Project>> projectWithSameName = this.projectRepository.findAllByName(project.getName());

        if(projectWithId.isPresent()) {

            if (projectWithSameName.isPresent()) {
                for(Project p : projectWithSameName.get()) {
                    if (!p.getId().equals(projectWithId.get().getId())) {
                        throw new ProjectCollectionException(ProjectCollectionException.ProjectAlreadyExists(project.getName()));
                    }
                }
            }

            Project projectToUpdate = projectWithId.get();

            projectToUpdate.setName(project.getName() != null ? project.getName() : projectToUpdate.getName());
            projectToUpdate.setDescription(project.getDescription() != null ? project.getDescription() : projectToUpdate.getDescription());
            projectToUpdate.setEmployees(project.getEmployees() != null ? project.getEmployees() : projectToUpdate.getEmployees());
            projectRepository.save(projectToUpdate);

            return new ResponseEntity<>(projectToUpdate, HttpStatus.OK);
        } else {
            throw new ProjectCollectionException(ProjectCollectionException.NotFoundException(id));
        }
    }

    @Override
    public ResponseEntity<Project> patchProjectById(String id, Project project) throws ProjectCollectionException {

        Optional<Project> project1 = this.projectRepository.findById(id);

        if(project1.isPresent()) {
            Project projectToPatch = project1.get();
            projectToPatch.setEmployees(project.getEmployees() != null ? project.getEmployees() : projectToPatch.getEmployees());
            projectRepository.save(projectToPatch);

            return new ResponseEntity<>(projectToPatch, HttpStatus.OK);
        } else {
            throw new ProjectCollectionException(ProjectCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteProjectById(String id) throws ProjectCollectionException {

        Optional<Project> projectToDelete = projectRepository.findById(id);

        if(projectToDelete.isEmpty()) {
            throw new ProjectCollectionException(ProjectCollectionException.NotFoundException(id));
        }else {
            projectRepository.deleteById(id);
        }
    }
}
