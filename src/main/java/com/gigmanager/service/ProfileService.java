package com.gigmanager.service;

import com.gigmanager.models.Profile;
import com.gigmanager.models.request.ProfileUpdateRequest;
import com.gigmanager.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProfileService {
    //1. Updating profile

    private final ProfileRepository profileRepository;

    public Profile readProfileByUsername(String username) {
        return profileRepository.findByApiUser_username(username).orElseThrow(EntityNotFoundException::new);
    }

    public void updateUser(ProfileUpdateRequest profileUpdateRequest, String username) {
        Profile updatedProfile = profileRepository.findByApiUser_username(username).orElseThrow(EntityNotFoundException::new);
        updatedProfile.setFirstName(profileUpdateRequest.getFirstName());
        updatedProfile.setLastName(profileUpdateRequest.getLastName());
        updatedProfile.setEmail(profileUpdateRequest.getEmail());
        updatedProfile.setPhone(profileUpdateRequest.getPhone());
        updatedProfile.setAddress(profileUpdateRequest.getAddress());
        profileRepository.save(updatedProfile);
    }


}
