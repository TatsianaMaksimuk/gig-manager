package com.gigmanager.controllers;

import com.gigmanager.models.Profile;
import com.gigmanager.models.request.ProfileUpdateRequest;
import com.gigmanager.repositories.ProfileRepository;
import com.gigmanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfilesController {

    private final ProfileRepository profileRepository;

    private final ProfileService profileService;

    //1. Get profile

    @GetMapping("/")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName(); // gets username from JWT token attached to the request

        return ResponseEntity.ok(profileRepository.findByApiUser_username(username).orElseThrow(EntityNotFoundException::new));
    }


    //2. Post (update)
    @PostMapping("/")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        profileService.updateUser(profileUpdateRequest, username);
        return ResponseEntity.ok().build();

    }
}
