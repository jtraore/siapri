package com.siapri.broker.app.views.common.customizer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Composite;

public interface ICustomizer<T> {

	public Composite createArea(Composite parent, int style);

	public String getTitle();

	public String getDescription();

	public void validateUpdate();

	public void cancelUpdate();

	public void addCustomizerStateListener(ICustomizerStateListener listener);

	public void removeCustomizerStateListener(ICustomizerStateListener listener);

	public IStatus getValidationStatus();

	public void dispose();
}
