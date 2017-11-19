package com.siapri.broker.app.views.contract;

import com.siapri.broker.business.model.Contract;

public class ContractDetail {

	private Contract contract;
	
	public ContractDetail(final Contract contract) {
		this.contract = contract;
	}
	
	public Contract getContract() {
		return contract;
	}
	
	public void setContract(final Contract contract) {
		this.contract = contract;
	}
	
}
