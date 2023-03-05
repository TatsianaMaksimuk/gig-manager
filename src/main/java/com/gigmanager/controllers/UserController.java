package com.gigmanager.controllers;

import com.gigmanager.models.request.UserCreateRequest;
import com.gigmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //dependency injection

    @PostMapping
    public ResponseEntity<?> createUser (@RequestBody UserCreateRequest userCreateRequest){
        userService.createUser(userCreateRequest);
        return ResponseEntity.ok().build(); //
    }
}
