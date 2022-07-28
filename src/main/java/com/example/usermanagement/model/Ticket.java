package com.example.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.usermanagement.enums.Priority;
import com.example.usermanagement.enums.Status;
import com.example.usermanagement.enums.Type;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@Data
@Document("api/v1/tickets")
public class Ticket {
    @Id
    private String id;

    @NotNull(message = "Ticket Title Cannot Be Null")
    private String title;

    @NotNull(message = "Ticket Description Cannot Be Null")
    private String description;

    private String projectName;

    private Type type;

    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="YYYY-LLL-dd", locale = "en_US") //Uses SimpleDateTimeFormat
    @DateTimeFormat(pattern="YYYY-LLL-dd")
    private String createdDate;

    private Priority priority;

    private String assignedTo;
}
