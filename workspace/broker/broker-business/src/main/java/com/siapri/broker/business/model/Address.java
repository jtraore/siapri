package com.siapri.broker.business.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class Address implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	public Address(final String number, final String street, final String additionalInfo, final String postalCode, final String city, final String country, final String comment) {
		this.number = number;
		this.street = street;
		this.additionalInfo = additionalInfo;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.comment = comment;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result + (country == null ? 0 : country.hashCode());
		result = prime * result + (number == null ? 0 : number.hashCode());
		result = prime * result + (postalCode == null ? 0 : postalCode.hashCode());
		result = prime * result + (street == null ? 0 : street.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Address)) {
			return false;
		}
		final Address other = (Address) obj;
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!number.equals(other.number)) {
			return false;
		}
		if (postalCode == null) {
			if (other.postalCode != null) {
				return false;
			}
		} else if (!postalCode.equals(other.postalCode)) {
			return false;
		}
		if (street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!street.equals(other.street)) {
			return false;
		}
		return true;
	}

}
