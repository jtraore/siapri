package com.siapri.broker.business.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "CONTRACT")
public class Contract extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "NUMBER", nullable = false)
	private String number;

	@Column(name = "SUBSCRIBTION_DATE")
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime subscribtionDate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "SINISTER_ID", referencedColumnName = "ID")
	private List<Sinister> sinisters = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private Customer customer;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "INSURANCE_TYPE_ID")
	private InsuranceType insuranceType;

	public Contract() {
	}

	public Contract(final String number, final ZonedDateTime subscribtionDate) {
		this.number = number;
		this.subscribtionDate = subscribtionDate;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(final String number) {
		this.number = number;
	}
	
	public ZonedDateTime getSubscribtionDate() {
		return subscribtionDate;
	}
	
	public void setSubscribtionDate(final ZonedDateTime subscribtionDate) {
		this.subscribtionDate = subscribtionDate;
	}
	
	public List<Sinister> getSinisters() {
		return sinisters;
	}
	
	public void setSinisters(final List<Sinister> sinisters) {
		this.sinisters = sinisters;
	}
	
}