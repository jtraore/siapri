package com.siapri.broker.app.views;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ToolbarSeperator {

	@PostConstruct
	public void createGui(final Composite parent) {
		new Label(parent, SWT.NONE).setText("        ");
	}
}