package com.ngosdi.lawyer.app.views.common.customizer;

@FunctionalInterface
public interface ICustomizerStateListener {

	public void customizerStateChanged(CustomizerStateEvent event);
}
