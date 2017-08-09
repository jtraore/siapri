package com.siapri.broker.app.views.common.proxy;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Proxy implements IProxy {

	private final Object target;
	private final PropertyChangeSupport propertyChangeSupport;

	public Proxy(final Object target) {
		this.target = target;
		propertyChangeSupport = new PropertyChangeSupport(target);
	}

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	@Override
	public Object getTarget() {
		return target;
	}

	@Override
	public void firePropertyChange(final String property, final Object oldValue, final Object newValue) {
		propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
	}

}
