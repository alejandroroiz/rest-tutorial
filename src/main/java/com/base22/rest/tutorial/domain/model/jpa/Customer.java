package com.base22.rest.tutorial.domain.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

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

  @Column
  private LocalDateTime createdDate;

  @Column
  private LocalDateTime lastUpdatedDate;

  public Customer(@NotEmpty(message = "'name' cannot be null or empty") String name,
      @NotEmpty(message = "'email' cannot be null or empty") @Email(message = "Must provide a valid email") String email,
      @NotEmpty(message = "'username' cannot be null or empty") String username,
      @NotEmpty(message = "'password' cannot be null or empty") String password, LocalDateTime createdDate,
      LocalDateTime lastUpdatedDate) {
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
    this.createdDate = createdDate;
    this.lastUpdatedDate = lastUpdatedDate;
  }

  public String getCreatedDate() {
    return createdDate != null ? createdDate.toString() : null;
  }

  public String getLastUpdatedDate() {
    return lastUpdatedDate != null ? lastUpdatedDate.toString() : null;
  }
}
