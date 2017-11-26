package com.siapri.broker.app.views.sinister;

import java.util.Date;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.customizer.propertybinding.converter.ZonedDateTimeToDateConverter;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Sinister;

public class SinisterCustomizerModel extends AbstractCustomizerModel<Sinister> {
	
	@EntityProperty(converter = ZonedDateTimeToDateConverter.class)
	private Date occuredDate;
	
	@EntityProperty
	private String description;
	
	@EntityProperty
	private Contract contract;
	
	private Address address;
	
	protected SinisterCustomizerModel() {
		super(null);
	}
	
	public SinisterCustomizerModel(final Sinister sinister) {
		super(sinister);
	}
	
	@Override
	public void synchronize() {
		super.synchronize();
		address = ProxyFactory.createProxy(target.getAddress());
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
