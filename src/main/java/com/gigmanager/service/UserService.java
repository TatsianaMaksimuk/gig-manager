package com.gigmanager.service;

import com.gigmanager.models.ApiUser;
import com.gigmanager.models.Profile;
import com.gigmanager.models.request.UserCreateRequest;
import com.gigmanager.repositories.ProfileRepository;
import com.gigmanager.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; //dependency injection

    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //searches user by username
    public ApiUser readUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }


    //1) Validation, does not allow to create user with existing username:
    //2) Creates a profile for user.
    public void createUser(UserCreateRequest userCreateRequest) {
        Optional<ApiUser> byUsername = userRepository.findByUsername(userCreateRequest.getUsername()); //optional is from jpa, is an object wrapper.
        // It provides a clear and explicit way to convey the message that there may not be a value, without using null.
        if (byUsername.isPresent()) { //true if found
            throw new RuntimeException("User with this username is already registered. Please use different username");
        }
        ApiUser newUser = new ApiUser();
        newUser.setUsername(userCreateRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        userRepository.save(newUser);

        Profile newUserProfile = new Profile(); //creating new profile
        newUserProfile.setApiUser(newUser); //setting newUser to new profile
        profileRepository.save(newUserProfile);

    }
}
