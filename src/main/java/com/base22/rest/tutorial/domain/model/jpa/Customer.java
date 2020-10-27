package com.base22.rest.tutorial.domain.model.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;

    public Customer(String name, String email, String username, String password )  {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}