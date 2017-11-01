package com.siapri.broker.app.views.sinister;

import java.time.ZonedDateTime;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Sinister;

public class SinisterCustomizerModel extends AbstractCustomizerModel<Sinister> {

	private Client client;
	private ZonedDateTime occurateDate;
	private String description;
	private Address address;

	public SinisterCustomizerModel() {
		super(null);
	}

	public SinisterCustomizerModel(final Sinister sinister) {
		super(sinister);

	}

	@Override
	protected void synchronize() {
		client = target.getClient();
		occurateDate = target.getOccurredDate();
		description = target.getDescription();
		address = target.getAddress();

	}

	@Override
	protected void validate() {
		target.setClient(client);
		target.setOccurredDate(occurateDate);
		target.setDescription(description);
		target.setAddress(address);

	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(final Client client) {
		this.client = client;
	}
	
	public ZonedDateTime getOccurateDate() {
		return occurateDate;
	}
	
	public void setOccurateDate(final ZonedDateTime occurateDate) {
		this.occurateDate = occurateDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(final Address address) {
		this.address = address;
	}
	
}
