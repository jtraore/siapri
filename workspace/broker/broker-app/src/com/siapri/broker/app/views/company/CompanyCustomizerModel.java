package com.siapri.broker.app.views.company;

import org.apache.commons.lang.StringUtils;

import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.proxy.IProxy;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Company;

public class CompanyCustomizerModel extends AbstractCustomizerModel<Company> {
	
	@EntityProperty
	private String siret;
	
	@EntityProperty
	private String name;
	
	@EntityProperty
	private String activity;

	@EntityProperty
	private String fax;

	private Address address;

	private String landPhone;

	private String mobilePhone;

	public CompanyCustomizerModel(final Company target) {
		super(target);
	}
	
	@Override
	public void synchronize() {
		super.synchronize();
		address = ProxyFactory.createProxy(target.getAddresses().getOrDefault(EAddressType.WORK.name(), new Address()));
		mobilePhone = target.getPhones().get(EPhoneType.MOBILE.name());
		landPhone = target.getPhones().get(EPhoneType.LAND.name());
	}

	@Override
	public void validate() {
		super.validate();
		if (StringUtils.isNotBlank(address.getStreet())) {
			target.getAddresses().put(EAddressType.WORK.name(), (Address) ((IProxy) address).getTarget());
		}
		target.getPhones().put(EPhoneType.LAND.name(), landPhone);
		if (StringUtils.isNotBlank(mobilePhone)) {
			target.getPhones().put(EPhoneType.LAND.name(), mobilePhone);
		}
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(final String siret) {
		this.siret = siret;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(final String activity) {
		this.activity = activity;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(final Address address) {
		this.address = address;
	}

	public String getLandPhone() {
		return landPhone;
	}

	public void setLandPhone(final String landPhone) {
		this.landPhone = landPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(final String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

}
