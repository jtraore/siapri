package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InsuranceSubjectAttribute implements ICodeDescriptionPair {

	@Column(name = "ATTRIBUTE_CODE")
	private String code;
	
	@Column(name = "ATTRIBUTE_DESCRIPTION")
	private String description;
	
	public InsuranceSubjectAttribute() {
	}

	public InsuranceSubjectAttribute(final String name, final String description) {
		code = name;
		this.description = description;
	}

	@Override
	public String getCode() {
		return code;
	}
	
	public void setCode(final String code) {
		this.code = code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	
}
