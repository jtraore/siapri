package com.siapri.broker.business.service.impl;

import com.siapri.broker.business.model.AbstractEntity;

public class DaoEvent {
	
	public static enum EventType {
		CREATE, UPDATE, DELETE
	}

	private final EventType type;
	
	private final AbstractEntity[] entities;;

	public DaoEvent(final EventType type, final AbstractEntity... entities) {
		if (entities.length == 0) {
			throw new IllegalArgumentException("DAO event entities must not be empty");
		}
		this.type = type;
		this.entities = entities;
	}
	
	public EventType getType() {
		return type;
	}
	
	public AbstractEntity[] getEntities() {
		return entities;
	}
}
