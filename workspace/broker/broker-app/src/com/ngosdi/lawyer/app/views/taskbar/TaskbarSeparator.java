package com.ngosdi.lawyer.app.views.taskbar;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TaskbarSeparator {

	@PostConstruct
	public void createGui(final Composite parent) {
		new Label(parent, SWT.NONE).setText("");
	}
}