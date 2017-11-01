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

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
	private Client client;
	
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Column(name = "OCCURRED_DATE")
	private ZonedDateTime occurredDate;
	
	@NotNull
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@NotNull
	@Embedded
	private Address address;
	
	public Sinister() {
	}
	
	public Sinister(final ZonedDateTime occurredDate, final String description) {
		this.occurredDate = occurredDate;
		this.description = description;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}
	
	public ZonedDateTime getOccurredDate() {
		return occurredDate;
	}
	
	public void setOccurredDate(final ZonedDateTime occurredDate) {
		this.occurredDate = occurredDate;
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

}
