package com.siapri.broker.business.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "CONTRACT")
public class Contract extends AbstractDocumentProvider {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "NUMBER", nullable = false)
	private String number;
	
	@Column(name = "SUBSCRIPTION_DATE")
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime subscriptionDate;
	
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "WARRANTY_FORMULA_ID"/* , nullable = false */)
	private WarrantyFormula warrantyFormula;
	
	public Contract() {
	}
	
	public Contract(final String number, final ZonedDateTime subscribtionDate) {
		this.number = number;
		subscriptionDate = subscribtionDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public ZonedDateTime getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(final ZonedDateTime subscribtionDate) {
		subscriptionDate = subscribtionDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public WarrantyFormula getWarrantyFormula() {
		return warrantyFormula;
	}

	public void setWarrantyFormula(final WarrantyFormula warrantyFormula) {
		this.warrantyFormula = warrantyFormula;
	}

}
