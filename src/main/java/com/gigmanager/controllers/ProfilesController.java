package com.gigmanager.controllers;

import com.gigmanager.models.Profile;
import com.gigmanager.models.request.ProfileUpdateRequest;
import com.gigmanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfilesController {
    private final ProfileService profileService;

    //1. Get profile

    @GetMapping("/")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName(); // gets username from JWT token attached to the request
        Profile profile = profileService.readProfileByUsername(username);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }


    //2. Post (update)
    @PostMapping("/")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        profileService.updateUser(profileUpdateRequest, username);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
