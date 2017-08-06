package com.siapri.broker.business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.siapri.broker.business.security.Profile;
import com.siapri.broker.business.security.User;

@Entity
@Table(name="BROKER")
@DiscriminatorValue("BROKER")
public class Broker extends User{
	
	private static final long serialVersionUID = 1L;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "BROKER_ID", referencedColumnName = "ID")
	List<Customer> managedCustomer = new ArrayList<>();
	
	public Broker(){}
	public Broker(String login, String password, String firstName, String lastName, String description, Profile profile){
		super(login, password, firstName, lastName, description, profile);
	}
	public List<Customer> getManagedCustomer() {
		return managedCustomer;
	}
	public void setManagedCustomer(List<Customer> managedCustomer) {
		this.managedCustomer = managedCustomer;
	}
	
	
}
