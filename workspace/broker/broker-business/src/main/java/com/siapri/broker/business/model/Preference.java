package com.siapri.broker.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PREFERENCE")
public class Preference extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;
	
	public Preference() {
	}
	
	public Preference(final String name, final String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
		value = defaultValue;
	}
	
	public final String getName() {
		return name;
	}
	
	public final void setName(final String name) {
		this.name = name;
	}
	
	public final String getValue() {
		return value;
	}
	
	public final void setValue(final String value) {
		this.value = value;
	}
	
	public final String getDefaultValue() {
		return defaultValue;
	}
	
	public final void setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
