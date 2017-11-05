package com.siapri.broker.app.views.insurancetype;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.Warranty;

public class WarrantyCustomizerModel extends AbstractCustomizerModel<Warranty> {
	
	public WarrantyCustomizerModel() {
		this(null);
	}
	
	public WarrantyCustomizerModel(final Warranty target) {
		super(target);
	}

	private String code;
	private String description;

	@Override
	public void synchronize() {
		code = target.getCode();
		description = target.getDescription();
	}

	@Override
	public void validate() {
		target.setCode(code);
		target.setDescription(description);
	}
	
	public String getName() {
		return code;
	}
	
	public void setName(final String name) {
		code = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
}
