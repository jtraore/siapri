package com.siapri.broker.app.views.insurer;

import java.util.HashMap;
import java.util.Map;

import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.InsuranceType;

public class InsurerDetail {
	
	private final Company insurer;
	private final Map<InsuranceType, Integer> contractNumberPerInsurance = new HashMap<>();
	private final Map<InsuranceType, Integer> sinisterNumberPerInsurance = new HashMap<>();

	public InsurerDetail(final Company insurer) {
		this.insurer = insurer;
	}
	
	public Company getInsurer() {
		return insurer;
	}
	
	public Map<InsuranceType, Integer> getContractNumberPerInsurance() {
		return contractNumberPerInsurance;
	}
	
	public Map<InsuranceType, Integer> getSinisterNumberPerInsurance() {
		return sinisterNumberPerInsurance;
	}

}
