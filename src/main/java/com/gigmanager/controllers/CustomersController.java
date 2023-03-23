package com.gigmanager.controllers;

import com.gigmanager.models.Customer;
import com.gigmanager.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomersController {
    //CRUD for customers

    private final CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<?> getAllCustomers(HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        List<Customer> customers = customerRepository.findAllByApiUser_username(username);
        return ResponseEntity.ok(customers);
    }

    //get customer by customer id



}
