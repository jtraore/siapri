package com.siapri.broker.business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "INSURANCE_WARRANTIES")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Warranty> warranties = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "INSURANCE_TYPE_ID", referencedColumnName = "ID")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<WarrantyFormula> formulas = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "INSURANCE_ATTRIBUTES")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<InsuranceSubjectAttribute> attributes = new ArrayList<>();

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

	public List<WarrantyFormula> getFormulas() {
		return formulas;
	}

	public void setFormulas(final List<WarrantyFormula> formulas) {
		this.formulas = formulas;
	}

	public List<InsuranceSubjectAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(final List<InsuranceSubjectAttribute> attributes) {
		this.attributes = attributes;
	}
	
}
