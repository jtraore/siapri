package com.siapri.broker.app.views.common.customizer.propertybinding.converter;

public interface IPropertyConverter<In, Out> {

	public Out fromEntity(final In obj);

	public In toEntity(final Out obj);
}
