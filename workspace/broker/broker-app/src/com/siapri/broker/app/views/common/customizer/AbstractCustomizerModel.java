package com.siapri.broker.app.views.common.customizer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.customizer.propertybinding.converter.IPropertyConverter;

public abstract class AbstractCustomizerModel<T> {
	
	protected T target;

	public AbstractCustomizerModel(final T target) {
		this(target, true);
	}
	
	public AbstractCustomizerModel(final T target, final boolean synchronize) {
		this.target = target;
		if (synchronize && target != null) {
			synchronize();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void synchronize() {
		getAnnotedFields().forEach(field -> {
			final EntityProperty entityProperty = field.getAnnotation(EntityProperty.class);
			final String entityPropertyName = entityProperty.name().isEmpty() ? field.getName() : entityProperty.name();
			try {
				final IPropertyConverter converter = entityProperty.converter().newInstance();
				final Field entityField = getField(entityPropertyName, target);
				setFieldValue(field, this, converter.fromEntity(getFieldValue(entityField, target)));
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void validate() {
		getAnnotedFields().forEach(field -> {
			final EntityProperty entityProperty = field.getAnnotation(EntityProperty.class);
			final String entityPropertyName = entityProperty.name().isEmpty() ? field.getName() : entityProperty.name();
			try {
				final IPropertyConverter converter = entityProperty.converter().newInstance();
				final Field entityField = getField(entityPropertyName, target);
				setFieldValue(entityField, target, converter.toEntity(getFieldValue(field, this)));
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		});
	}
	
	public T getTarget() {
		return target;
	}

	private static Object getFieldValue(final Field field, final Object obj) throws IllegalArgumentException, IllegalAccessException {
		final boolean accessible = field.isAccessible();
		field.setAccessible(true);
		final Object value = field.get(obj);
		field.setAccessible(accessible);
		return value;
	}
	
	private static void setFieldValue(final Field field, final Object obj, final Object value) throws IllegalArgumentException, IllegalAccessException {
		final boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.set(obj, value);
		field.setAccessible(accessible);
	}
	
	private static Field getField(final String fieldName, final Object obj) {
		Class<?> clazz = obj.getClass();
		Field field = null;
		while (clazz != null && field == null) {
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (final NoSuchFieldException e) {
			}
			clazz = clazz.getSuperclass();
		}
		return field;
	}
	
	private Set<Field> getAnnotedFields() {
		final Set<Field> fields = new HashSet<>();
		Class<?> clazz = getClass();
		while (clazz != null) {
			Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getAnnotation(EntityProperty.class) != null).forEach(fields::add);
			clazz = clazz.getSuperclass();
		}
		return fields;
	}
}
