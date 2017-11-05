package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Warranty {
	
	@Column(name = "CODE")
	private String code;

	@Column(name = "DESCRIPTION")
	private String description;

	public Warranty() {
	}
	
	public Warranty(final String name, final String description) {
		code = name;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
}
