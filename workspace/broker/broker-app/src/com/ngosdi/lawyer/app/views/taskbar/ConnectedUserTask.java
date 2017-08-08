package com.ngosdi.lawyer.app.views.taskbar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.ngosdi.lawyer.beans.User;
import com.ngosdi.lawyer.services.IAuthenticationService;

public class ConnectedUserTask {

	@Inject
	private IAuthenticationService authService;

	@PostConstruct
	public void createGui(final Composite parent, final IStylingEngine stylingEngine) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);
		final Label label = new Label(composite, SWT.NONE);
		stylingEngine.setId(parent, "Taskbar");
		stylingEngine.setId(label, "TaskbarLabel");
		final User currentUser = authService.getCurrentUser();
		label.setText(String.format("Connecté en tant que %s   ", currentUser.getLoginName()));
	}
}