package com.siapri.broker.app.views.broker;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.proxy.Data;
import com.siapri.broker.business.model.Broker;
import com.siapri.broker.business.security.Profile;

@Data
public class BrokerCustomizerModel extends AbstractCustomizerModel<Broker> {

	@EntityProperty
	private String number;
	
	@EntityProperty
	private String login;

	@EntityProperty
	private String password;

	@EntityProperty
	private String firstName;

	@EntityProperty
	private String lastName;

	@EntityProperty
	private String description;

	@EntityProperty
	private Profile profile;
	
	@EntityProperty
	private String phone;
	
	public BrokerCustomizerModel() {
		this(null);
	}
	
	public BrokerCustomizerModel(final Broker target) {
		super(target);
	}
	
}
