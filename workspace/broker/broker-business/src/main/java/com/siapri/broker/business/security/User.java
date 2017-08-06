package com.siapri.broker.business.security;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.siapri.broker.business.model.AbstractEntity;

@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("USER")
@DiscriminatorColumn(name = "USER_TYPE")
public class User extends AbstractEntity{


	private static final long serialVersionUID = 1L;
	
	@Size(min = 3, max = 32)
	@NotNull
	@Column(name = "LOGIN", nullable = false)
	private String login;
	
	@Size(min = 8)
	@NotNull
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Size(min = 1)
	@NotNull
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Size(min = 1)
	@NotNull
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "PROFILE", nullable = false)
	private Profile profile;
	
	public User(){}
	public User(String login, String password, String firstName, String lastName, String description, Profile profile){
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.profile = profile;
		
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	
	
}
