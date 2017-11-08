package com.siapri.broker.business.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Embeddable
public class WarrantyFormula {
	
	@Column(name = "NAME")
	private String name;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "INSURANCE_TYPE_ID")
	private InsuranceType insuranceType;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<String> warrantyCodes = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(final InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}
	
	public List<String> getWarrantyCodes() {
		return warrantyCodes;
	}
	
	public void setWarrantyCodes(final List<String> warrantyCodes) {
		this.warrantyCodes = warrantyCodes;
	}
	
}
