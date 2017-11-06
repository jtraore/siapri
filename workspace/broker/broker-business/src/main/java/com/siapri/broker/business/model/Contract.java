package com.siapri.broker.business.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "SINISTER_ID", referencedColumnName = "ID")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Sinister> sinisters = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	private Client client;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "INSURANCE_TYPE_ID")
	private InsuranceType insuranceType;

	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<String> warrantyCodes = new ArrayList<>();

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
	
	public List<Sinister> getSinisters() {
		return sinisters;
	}
	
	public void setSinisters(final List<Sinister> sinisters) {
		this.sinisters = sinisters;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(final Client client) {
		this.client = client;
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
