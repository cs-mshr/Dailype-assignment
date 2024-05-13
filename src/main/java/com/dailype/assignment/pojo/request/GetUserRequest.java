package com.dailype.assignment.pojo.request;

import lombok.Data;

import java.util.UUID;

@Data
public class GetUserRequest {
    private UUID user_id;
    private String mob_num;
    private UUID manager_id;
}
