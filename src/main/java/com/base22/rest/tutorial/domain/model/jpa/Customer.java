package com.base22.rest.tutorial.domain.model.jpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String name;

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  public Customer() {
  }

  public Customer(String name, String email, String username, String password) {
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
  }
}
