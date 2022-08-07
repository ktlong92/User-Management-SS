package com.example.usermanagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;

@Data
@Document("api/v1/projects")
public class Project {
    @Id
    private String id;

    @NotNull(message = "Title Cannot Be Null")
    private String title;

    @NotNull(message = "Project Description Cannot Be Null")
    private String description;

    private String employee = Employee.name;

}