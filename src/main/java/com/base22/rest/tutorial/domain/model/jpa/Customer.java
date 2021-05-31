package com.base22.rest.tutorial.domain.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
public class Customer {

	public Customer() { }

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

	public Long geId() { return this.id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return this.email; }
	public void setEmail(String email) { this.email = email; }

	public String getUsername() { return this.username; }
	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return this.password; }
	public void setPassword(String password) { this.password = password; }

	public String getCreatedDate() {
		return createdDate != null ? createdDate.toString() : null;
	}
	public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

	public String getLastUpdatedDate() {
		return lastUpdatedDate != null ? lastUpdatedDate.toString() : null;
	}
	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }

}