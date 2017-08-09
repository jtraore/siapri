package com.siapri.broker.app.views.common.customizer;

@FunctionalInterface
public interface ICustomizerStateListener {

	public void customizerStateChanged(CustomizerStateEvent event);
}
