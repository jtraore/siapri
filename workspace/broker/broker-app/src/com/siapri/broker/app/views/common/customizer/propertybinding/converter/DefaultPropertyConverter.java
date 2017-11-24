package com.siapri.broker.app.views.common.customizer.propertybinding.converter;

public class DefaultPropertyConverter implements IPropertyConverter<Object, Object> {

	@Override
	public Object toEntity(final Object obj) {
		return obj;
	}

	@Override
	public Object fromEntity(final Object obj) {
		return obj;
	}
}
