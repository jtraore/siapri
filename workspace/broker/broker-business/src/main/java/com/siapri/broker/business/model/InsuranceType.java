package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity(name = "INSURANCE_TYPE")
public class InsuranceType extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CODE", nullable = false)
	private String code;
	
	@NotNull
	@Column(name = "NAME", nullable = false)
	private String name;

	public InsuranceType() {
	}

	public InsuranceType(final String code, final String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
