package com.siapri.broker.app.views.client;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.customizer.propertybinding.converter.LocalDateToDateConverter;
import com.siapri.broker.app.views.common.proxy.Data;
import com.siapri.broker.app.views.common.proxy.IProxy;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Broker;
import com.siapri.broker.business.model.Gender;
import com.siapri.broker.business.model.Person;

@Data
public class ClientCustomizerModel extends AbstractCustomizerModel<Person> {

	@EntityProperty
	private String number;

	@EntityProperty
	private String firstName;

	@EntityProperty
	private String lastName;

	@EntityProperty
	private Gender gender;

	@EntityProperty(converter = LocalDateToDateConverter.class)
	private Date birthdate;
	
	@EntityProperty
	private Broker broker;

	@EntityProperty
	private String fax;
	
	private Address homeAddress;
	
	private Address workAddress;
	
	private String landPhone;
	
	private String mobilePhone;

	protected ClientCustomizerModel() {
		super(null);
	}

	public ClientCustomizerModel(final Person target) {
		super(target);
	}

	@Override
	public void synchronize() {
		super.synchronize();
		homeAddress = ProxyFactory.createProxy(target.getAddresses().getOrDefault(EAddressType.HOME.name(), new Address()));
		workAddress = ProxyFactory.createProxy(target.getAddresses().getOrDefault(EAddressType.WORK.name(), new Address()));
		mobilePhone = target.getPhones().get(EPhoneType.MOBILE.name());
		landPhone = target.getPhones().get(EPhoneType.LAND.name());
	}

	@Override
	public void validate() {
		super.validate();
		if (StringUtils.isNotBlank(homeAddress.getStreet())) {
			target.getAddresses().put(EAddressType.HOME.name(), (Address) ((IProxy) homeAddress).getTarget());
		}
		if (StringUtils.isNotBlank(workAddress.getStreet())) {
			target.getAddresses().put(EAddressType.WORK.name(), (Address) ((IProxy) workAddress).getTarget());
		}
		target.getPhones().put(EPhoneType.MOBILE.name(), mobilePhone);
		if (StringUtils.isNotBlank(landPhone)) {
			target.getPhones().put(EPhoneType.LAND.name(), landPhone);
		}
	}
	
}
