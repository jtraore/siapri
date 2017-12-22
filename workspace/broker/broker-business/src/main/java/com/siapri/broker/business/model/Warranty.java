package com.siapri.broker.business.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Warranty implements ICodeDescriptionPair, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "WARRANTY_CODE")
	private String code;
	
	@Column(name = "WARRANTY_DESCRIPTION")
	private String description;
	
	public Warranty() {
	}

	public Warranty(final String name, final String description) {
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
