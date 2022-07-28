package com.example.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.usermanagement.enums.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;

@Data
@Document("api/v1/projects")
public class Project {
    @Id
    private String id;

    @NotNull(message = "Project Name Cannot Be Null")
    private String projectName;

    @NotNull(message = "Project Description Cannot Be Null")
    private String projectDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="YYYY-LLL-dd", locale = "en_US") //Uses SimpleDateTimeFormat
    @DateTimeFormat(pattern="YYYY-LLL-dd")
    private String createdDate;

    private Status projectStatus;

}