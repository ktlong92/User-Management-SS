package com.example.usermanagement.model;

import com.example.usermanagement.enums.Priority;
import com.example.usermanagement.enums.Status;
import com.example.usermanagement.enums.Type;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String employees;

    private Type type;

    private Status status;

    private Priority priority;


}
