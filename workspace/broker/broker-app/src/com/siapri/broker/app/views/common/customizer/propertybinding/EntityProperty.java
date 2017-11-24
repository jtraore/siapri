package com.siapri.broker.app.views.common.customizer.propertybinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.siapri.broker.app.views.common.customizer.propertybinding.converter.DefaultPropertyConverter;
import com.siapri.broker.app.views.common.customizer.propertybinding.converter.IPropertyConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntityProperty {
	
	public String name() default "";
	
	public Class<? extends IPropertyConverter<?, ?>> converter() default DefaultPropertyConverter.class;
}
