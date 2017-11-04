package com.siapri.broker.business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "INSURANCE_TYPE")
public class InsuranceType extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "CODE", nullable = false)
	private String code;
	
	@NotNull
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "WARRANTY_ID", referencedColumnName = "ID")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Warranty> warranties = new ArrayList<>();
	
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
	
	public List<Warranty> getWarranties() {
		return warranties;
	}

	public void setWarranties(final List<Warranty> warranties) {
		this.warranties = warranties;
	}
	
}
