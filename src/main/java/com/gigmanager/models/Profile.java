package com.gigmanager.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long profileId;

    @OneToOne()
    @JoinColumn()
    private ApiUser apiUser;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
