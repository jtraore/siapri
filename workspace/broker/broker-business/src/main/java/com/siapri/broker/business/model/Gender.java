package com.siapri.broker.business.model;

public enum Gender implements ILabelProvider {
	
	FEMALE("Mme"), MALE("Mr");

	private String label;

	private Gender(final String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
