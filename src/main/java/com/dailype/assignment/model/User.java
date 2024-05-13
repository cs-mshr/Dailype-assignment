package com.dailype.assignment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class User {

    @Id
    private UUID user_id;

    private String full_name;
    private String mob_num;
    private String pan_num;
    private UUID manager_id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean isActive;
}