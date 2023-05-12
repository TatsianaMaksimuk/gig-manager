package com.gigmanager.service;

import com.gigmanager.models.ApiUser;
import com.gigmanager.models.Customer;
import com.gigmanager.models.request.CustomerUpsertRequest;
import com.gigmanager.repositories.CustomerRepository;
import com.gigmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public Customer createNewCustomer(CustomerUpsertRequest customerUpsertRequest, String username) {
        ApiUser user = userRepository.findByUsername(username).orElseThrow();
        Customer newCustomer = new Customer();
        newCustomer.setApiUser(user);
        newCustomer.setName(customerUpsertRequest.getName());
        newCustomer.setEmail(customerUpsertRequest.getEmail());
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public List<Customer> readAllCustomers(String username) {
        return customerRepository.findAllByApiUser_username(username);
    }

    public Customer findCustomerById(Long id, String username) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null || !customer.getApiUser().getUsername().equals(username)) {
            return null;
        }
        return customer;
    }


    public Customer updateCustomer(CustomerUpsertRequest customerUpsertRequest, Long id, String username) {
        Customer customer = findCustomerById(id, username);
        customer.setName(customerUpsertRequest.getName());
        customer.setEmail(customerUpsertRequest.getEmail());
        customerRepository.save(customer);
        return customer;
    }

    public void deleteCustomer(Long id, String username) {
        Customer customer = findCustomerById(id, username);
        if (customer != null) {
            customerRepository.deleteById(id);
        }
    }
}
