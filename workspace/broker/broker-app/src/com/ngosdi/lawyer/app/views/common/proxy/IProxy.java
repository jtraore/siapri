package com.ngosdi.lawyer.app.views.common.proxy;

import java.beans.PropertyChangeListener;

public interface IProxy {

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void removePropertyChangeListener(PropertyChangeListener listener);

	public void firePropertyChange(String property, Object oldValue, Object newValue);

	public Object getTarget();
}
