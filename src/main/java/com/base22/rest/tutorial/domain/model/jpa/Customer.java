package com.base22.rest.tutorial.domain.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty(message = "'name' cannot be null or empty")
  private String name;

  @NotEmpty(message = "'email' cannot be null or empty")
  @Email(message = "Must provide a valid email")
  @Column(unique = true)
  private String email;

  @NotEmpty(message = "'username' cannot be null or empty")
  @Column(unique = true)
  private String username;

  @NotEmpty(message = "'password' cannot be null or empty")
  private String password;

  public Customer(String name, String email, String username, String password) {
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
  }
}
