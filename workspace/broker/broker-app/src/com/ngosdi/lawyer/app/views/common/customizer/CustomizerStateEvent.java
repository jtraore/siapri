package com.ngosdi.lawyer.app.views.common.customizer;

import org.eclipse.core.runtime.IStatus;

public class CustomizerStateEvent {

	private final ICustomizer<?> customizer;
	private final IStatus status;

	public CustomizerStateEvent(final ICustomizer<?> customizer, final IStatus status) {
		this.customizer = customizer;
		this.status = status;
	}

	public ICustomizer<?> getCustomizer() {
		return customizer;
	}

	public IStatus getStatus() {
		return status;
	}
}
