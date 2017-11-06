package com.siapri.broker.app.views.insurancetype;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.InsuranceType;

public class InsuranceTypeCustomizerModel extends AbstractCustomizerModel<InsuranceType> {

	private String code;
	private String name;

	public InsuranceTypeCustomizerModel() {
		this(null);
	}

	public InsuranceTypeCustomizerModel(final InsuranceType target) {
		super(target);
	}
	
	@Override
	public void synchronize() {
		code = target.getCode();
		name = target.getName();
	}
	
	@Override
	public void validate() {
		target.setCode(code);
		target.setName(name);
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
