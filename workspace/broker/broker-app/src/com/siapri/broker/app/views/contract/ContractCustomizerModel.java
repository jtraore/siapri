package com.siapri.broker.app.views.contract;

import java.time.ZoneId;
import java.util.Date;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.WarrantyFormula;

public class ContractCustomizerModel extends AbstractCustomizerModel<Contract> {
	
	private String number;
	private Date subscriptionDate;
	private Client client;
	private InsuranceType insuranceType;
	private WarrantyFormula warrantyFormula;
	
	public ContractCustomizerModel() {
		this(null, null);
	}
	
	public ContractCustomizerModel(final Contract target, final InsuranceType insuranceType) {
		super(target);
		this.insuranceType = insuranceType;
	}

	@Override
	public void synchronize() {
		number = target.getNumber();
		if (target.getSubscriptionDate() != null) {
			subscriptionDate = Date.from(target.getSubscriptionDate().toInstant());
		}
		client = target.getClient();
		warrantyFormula = target.getWarrantyFormula();
	}

	@Override
	public void validate() {
		target.setNumber(number);
		target.setSubscriptionDate(subscriptionDate.toInstant().atZone(ZoneId.systemDefault()));
		target.setClient(client);
		// target.setInsuranceType(insuranceType);
		target.setWarrantyFormula(warrantyFormula);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(final Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(final InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}
	
	public WarrantyFormula getWarrantyFormula() {
		return warrantyFormula;
	}
	
	public void setWarrantyFormula(final WarrantyFormula warrantyFormula) {
		this.warrantyFormula = warrantyFormula;
	}

}
