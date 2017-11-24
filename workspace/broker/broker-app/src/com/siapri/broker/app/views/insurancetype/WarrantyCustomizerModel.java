package com.siapri.broker.app.views.insurancetype;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.business.model.Warranty;

public class WarrantyCustomizerModel extends AbstractCustomizerModel<Warranty> {
	
	@EntityProperty
	private String code;

	@EntityProperty
	private String description;
	
	public WarrantyCustomizerModel() {
		this(null);
	}
	
	public WarrantyCustomizerModel(final Warranty target) {
		super(target);
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(final String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
}
