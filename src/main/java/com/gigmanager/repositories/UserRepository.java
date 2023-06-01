package com.gigmanager.repositories;

import com.gigmanager.models.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApiUser, Long> {
    //custom method (query) to find user by username:
    Optional<ApiUser> findByUsername(String username);
}
