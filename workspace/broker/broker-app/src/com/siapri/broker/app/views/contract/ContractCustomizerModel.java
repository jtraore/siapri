package com.siapri.broker.app.views.contract;

import java.time.ZoneId;
import java.util.Date;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;

public class ContractCustomizerModel extends AbstractCustomizerModel<Contract> {
	
	private String number;
	private Date subscriptionDate;
	private Client client;
	private InsuranceType insuranceType;
	
	public ContractCustomizerModel() {
		this(null);
	}
	
	public ContractCustomizerModel(final Contract target) {
		super(target);
	}

	@Override
	public void synchronize() {
		number = target.getNumber();
		if (target.getSubscriptionDate() != null) {
			subscriptionDate = Date.from(target.getSubscriptionDate().toInstant());
		}
		client = target.getClient();
		// insuranceType = target.getInsuranceType();
	}

	@Override
	public void validate() {
		target.setNumber(number);
		target.setSubscriptionDate(subscriptionDate.toInstant().atZone(ZoneId.systemDefault()));
		target.setClient(client);
		// target.setInsuranceType(insuranceType);
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

}
