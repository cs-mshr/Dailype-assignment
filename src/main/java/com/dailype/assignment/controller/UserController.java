package com.dailype.assignment.controller;

import com.dailype.assignment.model.User;
import com.dailype.assignment.pojo.request.CreateUserRequest;
import com.dailype.assignment.pojo.request.DeletUserRequest;
import com.dailype.assignment.pojo.request.GetUserRequest;
import com.dailype.assignment.pojo.response.CreateUserResponse;
import com.dailype.assignment.pojo.response.DeleteUserResponse;
import com.dailype.assignment.pojo.response.GetUserResponse;
import com.dailype.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
        return createUserResponse;
    }

    @PostMapping("/get_users")
    public GetUserResponse getUsers(@RequestBody(required = false) GetUserRequest getUserRequest) {
        GetUserResponse getUserResponse = userService.getUsers(getUserRequest);
        return getUserResponse;
    }

    @PostMapping("/delete_user")
    public DeleteUserResponse deleteUser(@RequestBody(required = false) DeletUserRequest deleteUserRequest) {
        DeleteUserResponse deleteUserResponse = userService.deleteUser(deleteUserRequest);
        return deleteUserResponse;
    }



}
