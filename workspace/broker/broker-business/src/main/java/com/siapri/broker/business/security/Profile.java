package com.siapri.broker.business.security;

import com.siapri.broker.business.model.ILabelProvider;

public enum Profile implements ILabelProvider {

	ADMIN("Administrateur"), USER("Utilisateur");
	
	private String label;
	
	private Profile(final String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

}
