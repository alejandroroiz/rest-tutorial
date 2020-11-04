package com.base22.rest.tutorial.configuration;

import java.util.ArrayList;

import com.base22.rest.tutorial.domain.model.jpa.Customer;
import com.base22.rest.tutorial.domain.repository.jpa.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository repository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);

        }
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(),
                new ArrayList<>());
    }

    public Customer save(Customer user) {
        Customer newUser = new Customer();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.save(newUser);
    }
}