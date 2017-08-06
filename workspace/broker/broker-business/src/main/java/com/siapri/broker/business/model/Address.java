package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ADDRESS")
public class Address extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", nullable = true)
	@Size(max = 25)
	private String name;
	
	@Column(name = "ROAD_NUMBER", nullable = true)
	private String roadNumber;
	
	@Column(name = "ROAD", nullable = true)
	private String road;
	
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
	
	public Address(){}
	
	public Address(String name, String roadNumber, String road, String postalCode, String city, String country, String comment){
		this.name = name;
		this.roadNumber = roadNumber;
		this.road = road;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
