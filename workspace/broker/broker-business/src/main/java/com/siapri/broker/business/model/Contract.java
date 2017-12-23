package com.siapri.broker.business.model;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@Column(name = "SIGNED")
	private boolean signed;
	
	@Column(name = "PROPOSAL_DATE")
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime proposalDate;

	@Column(name = "SUBSCRIPTION_DATE")
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime subscriptionDate;
	
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	private Client client;

	@ManyToOne
	@JoinColumn(name = "WARRANTY_FORMULA_ID"/* , nullable = false */)
	private WarrantyFormula warrantyFormula;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@CollectionTable(name = "CONTRACT_SUBJECT_ATTRIBUTES")
	private Map<String, String> subjectAttributeCodes = new HashMap<>();

	@ManyToOne
	@JoinColumn(name = "INSURER_ID"/* , nullable = false */)
	private Company insurer;

	@ManyToOne
	@JoinColumn(name = "BROKER_ID", referencedColumnName = "ID")
	private Broker broker;
	
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
	
	public boolean isSigned() {
		return signed;
	}
	
	public void setSigned(final boolean signed) {
		this.signed = signed;
	}

	public ZonedDateTime getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(final ZonedDateTime subscribtionDate) {
		subscriptionDate = subscribtionDate;
	}
	
	public ZonedDateTime getProposalDate() {
		return proposalDate;
	}
	
	public void setProposalDate(final ZonedDateTime proposalDate) {
		this.proposalDate = proposalDate;
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

	public Map<String, String> getSubjectAttributeCodes() {
		return subjectAttributeCodes;
	}

	public void setSubjectAttributeCodes(final Map<String, String> subjectAttributeCodes) {
		this.subjectAttributeCodes = subjectAttributeCodes;
	}

	public Company getInsurer() {
		return insurer;
	}

	public void setInsurer(final Company insurer) {
		this.insurer = insurer;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(final Broker broker) {
		this.broker = broker;
	}
}
