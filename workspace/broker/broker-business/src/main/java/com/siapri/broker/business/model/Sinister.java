package com.siapri.broker.business.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "SINISTER")
public class Sinister extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@Column(name = "OCCURRED_DATE")
	private ZonedDateTime occurredDate;
	
	@NotNull
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	public Sinister(){}
	
	public Sinister(ZonedDateTime occurredDate, String description){
		this.occurredDate = occurredDate;
		this.description = description;
	}

	public ZonedDateTime getOccurredDate() {
		return occurredDate;
	}

	public void setOccurredDate(ZonedDateTime occurredDate) {
		this.occurredDate = occurredDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
