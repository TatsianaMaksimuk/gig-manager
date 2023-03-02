package com.gigmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebConfiguration {

    //Introducing BCryptPasswordEncoder as a bean in app.
        //Java bean is just like regular class, except it follows these conventions:
        // 1) All properties are private (needs getters/setters)
        // 2) Has public no-argument constructor
        // 3) Implements Serializable
        // it is used to store business logic

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
