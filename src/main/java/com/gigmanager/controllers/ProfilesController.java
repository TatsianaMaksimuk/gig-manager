package com.gigmanager.controllers;

import com.gigmanager.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfilesController {

    private final ProfileRepository profileRepository;

    //1. Get profile

    @GetMapping("/")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {

        String userName = request.getUserPrincipal().getName(); // gets username from JWT token attached to the request

        return ResponseEntity.ok(profileRepository.findByApiUser_username(userName).orElseThrow(EntityNotFoundException::new));
    }


    //2. Post (update)
}
