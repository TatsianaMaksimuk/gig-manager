package com.gigmanager.service;

import com.gigmanager.models.ApiUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {
    private final UserService userService;

    //We're overriding loadUserByUsername() by using UserService we created earlier to read user by username:

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        ApiUser apiUser = userService.readUserByUsername(username);

        if (apiUser == null){
            throw new UsernameNotFoundException(username);
        }

        // setting our user’s username and password with role list
        // to the org.springframework.security.core.userdetails.User.
        return new User(apiUser.getUsername(), apiUser.getPassword(), Collections.emptyList());
    }
}
