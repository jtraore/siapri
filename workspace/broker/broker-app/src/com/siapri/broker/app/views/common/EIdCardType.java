package com.siapri.broker.app.views.common;

public enum EIdCardType {

	PASSPORT("Passport"), NATIONAL_ID_CARD("Carte d'identité nationale"), DRIVE_LICENCE("Permis");

	private String label;

	private EIdCardType(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
