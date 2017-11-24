package com.siapri.broker.app.views.insurancetype;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.business.model.InsuranceType;

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

	public String getCode() {
		return code;
	}
	
	public void setCode(final String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
}
