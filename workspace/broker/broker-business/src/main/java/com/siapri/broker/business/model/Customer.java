package com.siapri.broker.business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CUSTOMER")
public class Customer extends AbstractDocumentProvider {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "FIRST_NAME", nullable = false)
	@Size(min = 1)
	private String firstName;

	@NotNull
	@Column(name = "LAST_NAME", nullable = false)
	@Size(min = 1)
	private String lastName;

	@NotNull
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
	private List<Address> addresses = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "SINISTER_ID", referencedColumnName = "ID")
	private final List<Sinister> sinisters = new ArrayList<>();

	public Customer() {
	}

	public Customer(final String firstName, final String lastName, final List<Address> addresses) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
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
	
	public List<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(final List<Address> addresses) {
		this.addresses = addresses;
	}
	
}
