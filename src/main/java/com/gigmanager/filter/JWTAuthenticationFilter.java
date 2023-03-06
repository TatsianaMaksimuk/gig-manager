package com.gigmanager.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigmanager.models.ApiUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //Servlets are the Java programs that run on the Java-enabled web server or application server.
    //They are used to handle the request obtained from the webserver, process the request, produce the response, then send a response back to the webserver.
    //HttpServletRequest provides methods for accessing parameters of a request

    @Override public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        try {
            ApiUser credentials = new ObjectMapper().readValue(request.getInputStream(), ApiUser.class);

            return authenticationManager.authenticate( //following parameters
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
