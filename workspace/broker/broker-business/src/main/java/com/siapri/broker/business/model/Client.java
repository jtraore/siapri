package com.siapri.broker.business.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CLIENT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "CLIENT_TYPE")
public abstract class Client extends AbstractDocumentProvider {

	private static final long serialVersionUID = 1L;

	@Column(name = "NUMBER")
	private String number;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "PHONE_TYPE")
	@Column(name = "PHONE_NUMBER")
	@CollectionTable(name = "CUSTOMER_PHONES")
	private final Map<String, String> phones = new HashMap<>();

	@NotNull
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "CUSTOMER_ADDRESSES")
	private final Map<String, Address> addresses = new HashMap<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "EMAIL_TYPE")
	@Column(name = "EMAIL")
	@CollectionTable(name = "CUSTOMER_EMAILS")
	private final Map<String, String> emails = new HashMap<>();

	@Column(name = "FAX")
	private String fax;

	@ManyToOne
	@JoinColumn(name = "BROKER_ID", referencedColumnName = "ID")
	private Broker broker;

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}
	
	public Map<String, String> getPhones() {
		return phones;
	}
	
	public Map<String, Address> getAddresses() {
		return addresses;
	}
	
	public Map<String, String> getEmails() {
		return emails;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public Broker getBroker() {
		return broker;
	}

	public void setBroker(final Broker broker) {
		this.broker = broker;
	}
}
