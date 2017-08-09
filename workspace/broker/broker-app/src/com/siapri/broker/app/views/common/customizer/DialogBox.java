package com.siapri.broker.app.views.common.customizer;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.siapri.broker.app.views.common.EImage;
import com.siapri.broker.app.views.common.TitledSeparator;

public class DialogBox extends TitleAreaDialog {

	public DialogBox(final Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Lawyer");
		newShell.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	@Override
	protected Button createButton(final Composite parent, final int id, final String label, final boolean defaultButton) {
		final Button button = super.createButton(parent, id, label, defaultButton);
		if (id == OK) {
			button.setText("Valider");
			button.setImage(EImage.OK.getSwtImage());

		} else {
			button.setText("Annuler");
			button.setImage(EImage.NOK.getSwtImage());
		}
		return button;
	}

	@Override
	protected Control createButtonBar(final Composite parent) {
		final TitledSeparator separator = new TitledSeparator(parent, "");
		final GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
		return super.createButtonBar(parent);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// @Override
	// protected Point getInitialSize() {
	// return new Point(800, 600);
	// }

}