package com.gigmanager.repositories;

import com.gigmanager.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByApiUser_username(String username);
}
