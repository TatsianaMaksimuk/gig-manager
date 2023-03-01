package com.gigmanager.models;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class ApiUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;


    //Constructors

    public ApiUser(){

    }


    public ApiUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    //Getters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


