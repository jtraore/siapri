package com.siapri.broker.business.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CONVERSATION")
public class Conversation extends AbstractDocumentProvider {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CONVERSATION_DATE")
	@org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime date;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "DIRECTION")
	private Direction direction;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE")
	private Type type;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contract contract;
	
	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(final ZonedDateTime date) {
		this.date = date;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(final Direction direction) {
		this.direction = direction;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(final Type type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public Contract getContract() {
		return contract;
	}
	
	public void setContract(final Contract contract) {
		this.contract = contract;
	}
	
	public static enum Direction {
		IN, OUT
	}

	public static enum Type {
		PHONE, LETTER, EMAIL, MEET
	}
}