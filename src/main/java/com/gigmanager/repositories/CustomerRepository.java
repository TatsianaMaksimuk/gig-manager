package com.gigmanager.repositories;

import com.gigmanager.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByApiUser_username(String username);
}
