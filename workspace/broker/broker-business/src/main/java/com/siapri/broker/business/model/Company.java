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
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ACTIVITY")
	private String activity;

	@Column(name = "INSURER")
	private boolean insurer;

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
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}

	public String getActivity() {
		return activity;
	}
	
	public void setActivity(final String activity) {
		this.activity = activity;
	}

	public boolean isInsurer() {
		return insurer;
	}

	public void setInsurer(final boolean insurer) {
		this.insurer = insurer;
	}
}
