package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "COMPANY")
@DiscriminatorValue("COMPANY")
public class Company extends Client {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "SIRET")
	private String siret;

	public Company() {
	}

	public Company(final String siret) {
		this.siret = siret;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(final String siret) {
		this.siret = siret;
	}
}
