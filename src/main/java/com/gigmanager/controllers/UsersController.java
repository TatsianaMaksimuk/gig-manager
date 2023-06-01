package com.gigmanager.controllers;

import com.gigmanager.models.request.UserCreateRequest;
import com.gigmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    //dependency injection

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
