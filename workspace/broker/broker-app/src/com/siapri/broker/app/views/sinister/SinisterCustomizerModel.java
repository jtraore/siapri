package com.siapri.broker.app.views.sinister;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Sinister;

public class SinisterCustomizerModel extends AbstractCustomizerModel<Sinister> {
	
	private Date occuredDate;
	private String description;
	private Address address;

	private Contract contract;

	protected SinisterCustomizerModel() {
		super(null);
	}
	
	public SinisterCustomizerModel(final Sinister sinister, final Contract contract) {
		super(sinister);
		this.contract = contract;
	}
	
	@Override
	public void synchronize() {
		if (target.getOccurredDate() != null) {
			occuredDate = Date.from(target.getOccurredDate().toInstant());
		}
		description = target.getDescription();
		address = target.getAddress();
		
	}
	
	@Override
	public void validate() {
		if (!contract.getSinisters().contains(target)) {
			contract.getSinisters().add(target);
		}
		target.setOccurredDate(ZonedDateTime.ofInstant(occuredDate.toInstant(), ZoneId.systemDefault()));
		target.setDescription(description);
		target.setAddress(address);
		
	}

	public Date getOccurateDate() {
		return occuredDate;
	}

	public void setOccurateDate(final Date occurateDate) {
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

	public Date getOccuredDate() {
		return occuredDate;
	}

	public void setOccuredDate(final Date occuredDate) {
		this.occuredDate = occuredDate;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(final Contract contract) {
		this.contract = contract;
	}

}
