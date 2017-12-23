package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.siapri.broker.business.security.Profile;
import com.siapri.broker.business.security.User;

@Entity
@Table(name = "BROKER")
@DiscriminatorValue("BROKER")
public class Broker extends User {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String phone;
	
	public Broker() {
	}

	public Broker(final String login, final String password, final String firstName, final String lastName, final String description, final Profile profile) {
		super(login, password, firstName, lastName, description, profile);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

}
