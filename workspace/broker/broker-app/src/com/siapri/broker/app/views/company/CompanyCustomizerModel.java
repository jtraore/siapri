package com.siapri.broker.app.views.company;

import org.apache.commons.lang.StringUtils;

import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.proxy.Data;
import com.siapri.broker.app.views.common.proxy.IProxy;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Company;

@Data
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
	
	protected CompanyCustomizerModel() {
		super(null);
	}

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
			target.getPhones().put(EPhoneType.MOBILE.name(), mobilePhone);
		}
	}

}
