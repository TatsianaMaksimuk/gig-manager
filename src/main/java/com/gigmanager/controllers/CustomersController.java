package com.gigmanager.controllers;

import com.gigmanager.models.ApiUser;
import com.gigmanager.models.Customer;
import com.gigmanager.models.request.CustomerUpsertRequest;
import com.gigmanager.repositories.CustomerRepository;
import com.gigmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomers(HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        List<Customer> customers = customerRepository.findAllByApiUser_username(username);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //get customer by customer id
    // found customer should belong to current user
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById (@PathVariable Long id, HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        Customer requestedCustomer = customerRepository.getById(id);
        if (!requestedCustomer.getApiUser().getUsername().equals(username)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);
    }

    //create customer
    @PostMapping("/")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerUpsertRequest customerUpsertRequest, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        ApiUser user = userRepository.findByUsername(username).orElseThrow();
        Customer newCustomer = new Customer();
        newCustomer.setApiUser(user);
        newCustomer.setName(customerUpsertRequest.getName());
        newCustomer.setEmail(customerUpsertRequest.getEmail());
        customerRepository.save(newCustomer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);

    }

    //update customer
   @PostMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpsertRequest customerUpsertRequest, HttpServletRequest request) {
       String username = request.getUserPrincipal().getName();
       Customer requestedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
       if (!requestedCustomer.getApiUser().getUsername().equals(username)){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       requestedCustomer.setName(customerUpsertRequest.getName());
       requestedCustomer.setEmail(customerUpsertRequest.getEmail());
       customerRepository.save(requestedCustomer);
       return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);
   }



}
