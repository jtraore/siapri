package com.siapri.broker.business.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "PERSON")
@DiscriminatorValue("PERSON")
public class Person extends Client {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER")
	private Gender gender;

	@NotNull
	@Column(name = "FIRST_NAME", nullable = false)
	@Size(min = 1)
	private String firstName;

	@NotNull
	@Column(name = "LAST_NAME", nullable = false)
	@Size(min = 1)
	private String lastName;

	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
	@Column(name = "BIRTHDATE")
	private LocalDate birthdate;
	
	public Person() {
	}
	
	public Person(final String firstName, final String lastName, final Gender gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(final Gender gender) {
		this.gender = gender;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(final LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
}
