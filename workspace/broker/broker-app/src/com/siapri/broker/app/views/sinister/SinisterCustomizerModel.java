package com.siapri.broker.app.views.sinister;

import java.time.ZonedDateTime;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Sinister;

public class SinisterCustomizerModel extends AbstractCustomizerModel<Sinister> {
	
	private ZonedDateTime occuredDate;
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
		occuredDate = target.getOccurredDate();
		description = target.getDescription();
		address = target.getAddress();
		
	}
	
	@Override
	protected void validate() {
		target.setOccurredDate(occuredDate);
		target.setDescription(description);
		target.setAddress(address);
		
	}

	public ZonedDateTime getOccurateDate() {
		return occuredDate;
	}

	public void setOccurateDate(final ZonedDateTime occurateDate) {
		occuredDate = occurateDate;
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
