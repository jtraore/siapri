package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class Address {

	@Column(name = "LABEL", nullable = true)
	@Size(max = 25)
	private String label;

	@Column(name = "NUMBER", nullable = true)
	private String number;

	@Column(name = "STREET", nullable = true)
	private String street;

	@Column(name = "ADDITIONAL_INFO")
	private String additionalInfo;

	@Column(name = "POSTAL_CODE", nullable = true)
	private String postalCode;

	@Size(min = 1)
	@Column(name = "CITY", nullable = false)
	private String city;

	@Size(min = 1)
	@Column(name = "COUNTRY", nullable = false)
	private String country;

	@Column(name = "COMMENT", nullable = true)
	private String comment;

	public Address() {
	}

	public Address(final String label, final String number, final String street, final String additionalInfo, final String postalCode, final String city, final String country, final String comment) {
		this.label = label;
		this.number = number;
		this.street = street;
		this.additionalInfo = additionalInfo;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.comment = comment;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(final String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

}
