package com.cocktails.user.service.impl;

import com.cocktails.user.model.Customer;
import com.cocktails.user.repository.CustomerRepository;
import com.cocktails.user.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer register(Customer customer) {
        customer.setPwd(passwordEncoder.encode(customer.getPwd()));
        return customerRepository.save(customer);
    }
}
