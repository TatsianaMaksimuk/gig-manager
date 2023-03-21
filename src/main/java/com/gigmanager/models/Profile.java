package com.gigmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "profile") //table profile will have column "user_id", that will target id column in the user table
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName="id")
    @JsonIgnoreProperties("password")
    private ApiUser apiUser;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
