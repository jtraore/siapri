package com.siapri.broker.business.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "SINISTER")
public class Sinister extends AbstractDocumentProvider {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "SINISTER_NUMBER", nullable = false)
	private String number;
	
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Column(name = "OCCURRED_DATE")
	private ZonedDateTime occuredDate;
	
	@NotNull
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@NotNull
	@Embedded
	private Address address;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contract contract;
	
	public Sinister() {
	}
	
	public Sinister(final ZonedDateTime occurredDate, final String description) {
		occuredDate = occurredDate;
		this.description = description;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}
	
	public ZonedDateTime getOccuredDate() {
		return occuredDate;
	}
	
	public void setOccuredDate(final ZonedDateTime occuredDate) {
		this.occuredDate = occuredDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(final Address address) {
		this.address = address;
	}
	
	public Contract getContract() {
		return contract;
	}
	
	public void setContract(final Contract contract) {
		this.contract = contract;
	}
	
}
