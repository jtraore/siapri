package com.siapri.broker.app.views.contract;

import java.util.ArrayList;
import java.util.List;

import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Sinister;

public class ContractDetail {
	
	private final Contract contract;
	private final List<Sinister> sinisters = new ArrayList<>();

	public ContractDetail(final Contract contract, final List<Sinister> sinisters) {
		this.contract = contract;
		this.sinisters.addAll(sinisters);
	}

	public Contract getContract() {
		return contract;
	}

	public List<Sinister> getSinisters() {
		return sinisters;
	}

}
