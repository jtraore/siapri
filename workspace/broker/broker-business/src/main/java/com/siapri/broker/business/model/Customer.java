package com.siapri.broker.business.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends AbstractDocumentProvider {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER")
	private TypeCustomer gender;

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
	
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "PHONE_TYPE")
	@Column(name = "PHONE_NUMBER")
	@CollectionTable(name = "CUSTOMER_PHONES")
	private final Map<String, String> phones = new HashMap<>();
	
	@NotNull
	@ElementCollection(fetch = FetchType.EAGER)
	// @Fetch(value = FetchMode.SUBSELECT)
	@CollectionTable(name = "CUSTOMER_ADDRESSES")
	private final Map<String, Address> addresses = new HashMap<>();
	
	@ManyToOne
	@JoinColumn(name = "BROKER_ID", referencedColumnName = "ID")
	private Broker broker;

	public Customer() {
	}

	public Customer(final String firstName, final String lastName, final TypeCustomer gender) {
		this.firstName = firstName;
		this.lastName = lastName;
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
	
	public Map<String, String> getPhones() {
		return phones;
	}

	public Map<String, Address> getAddresses() {
		return addresses;
	}
}
