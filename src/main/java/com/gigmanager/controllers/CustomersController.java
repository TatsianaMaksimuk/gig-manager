package com.gigmanager.controllers;

import com.gigmanager.models.Customer;
import com.gigmanager.models.request.CustomerUpsertRequest;
import com.gigmanager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomersController {
    //CRUD for customers

    private final CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomers(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        List<Customer> customers = customerService.readAllCustomers(username);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    //get customer by customer id
    // found customer should belong to current user
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Customer requestedCustomer = customerService.findCustomerById(id, username);
        if (requestedCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);
    }

    //create customer
    @PostMapping("/")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerUpsertRequest customerUpsertRequest, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Customer newCustomer = customerService.createNewCustomer(customerUpsertRequest, username);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    //update customer
    @PostMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpsertRequest customerUpsertRequest, @PathVariable Long id,  HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Customer requestedCustomer = customerService.updateCustomer(customerUpsertRequest, id, username);
        if (requestedCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(requestedCustomer, HttpStatus.OK);
    }


    //delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        customerService.deleteCustomer(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
