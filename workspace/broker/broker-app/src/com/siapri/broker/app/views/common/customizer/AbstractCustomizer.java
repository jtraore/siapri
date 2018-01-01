package com.siapri.broker.app.views.common.customizer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import com.siapri.broker.app.views.common.customizer.databinding.BindingSupport;

public abstract class AbstractCustomizer<T> implements ICustomizer<T>, IChangeListener {

	private final String title;
	private final String description;
	protected final T object;

	protected final BindingSupport bindingSupport;
	protected final List<ICustomizerStateListener> customizerStateListeners;

	protected IStatus validationStatus;

	public AbstractCustomizer(final T object, final String title, final String description) {
		this.object = object;
		this.title = title;
		this.description = description;
		validationStatus = ValidationStatus.ok();
		bindingSupport = new BindingSupport();
		bindingSupport.addChangeListener(this);
		customizerStateListeners = new ArrayList<>();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void handleChange(final ChangeEvent event) {
		validationStatus = bindingSupport.getValidationStatus();
		final CustomizerStateEvent customizerStateEvent = new CustomizerStateEvent(this, validationStatus);
		fireCustomizerStateEvent(customizerStateEvent);
	}

	@Override
	public void addCustomizerStateListener(final ICustomizerStateListener listener) {
		customizerStateListeners.add(listener);
	}

	@Override
	public void removeCustomizerStateListener(final ICustomizerStateListener listener) {
		customizerStateListeners.remove(listener);
	}

	@Override
	public IStatus getValidationStatus() {
		return validationStatus;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	private void fireCustomizerStateEvent(final CustomizerStateEvent event) {
		for (final ICustomizerStateListener listener : customizerStateListeners) {
			listener.customizerStateChanged(event);
		}
	}
}
