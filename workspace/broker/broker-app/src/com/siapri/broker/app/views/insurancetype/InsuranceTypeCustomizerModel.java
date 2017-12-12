package com.siapri.broker.app.views.insurancetype;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.proxy.Data;
import com.siapri.broker.business.model.InsuranceType;

@Data
public class InsuranceTypeCustomizerModel extends AbstractCustomizerModel<InsuranceType> {

	@EntityProperty
	private String code;
	
	@EntityProperty
	private String name;

	public InsuranceTypeCustomizerModel() {
		this(null);
	}

	public InsuranceTypeCustomizerModel(final InsuranceType target) {
		super(target);
	}
	
}
