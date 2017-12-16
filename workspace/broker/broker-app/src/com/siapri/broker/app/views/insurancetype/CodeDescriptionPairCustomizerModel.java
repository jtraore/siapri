package com.siapri.broker.app.views.insurancetype;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.proxy.Data;
import com.siapri.broker.business.model.ICodeDescriptionPair;

@Data
public class CodeDescriptionPairCustomizerModel<T extends ICodeDescriptionPair> extends AbstractCustomizerModel<T> {

	@EntityProperty
	private String code;
	
	@EntityProperty
	private String description;

	public CodeDescriptionPairCustomizerModel() {
		this(null);
	}

	public CodeDescriptionPairCustomizerModel(final T target) {
		super(target);
	}
}
