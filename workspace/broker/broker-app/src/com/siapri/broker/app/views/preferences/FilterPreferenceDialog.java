package com.siapri.broker.app.views.preferences;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.EImage;

public class FilterPreferenceDialog extends PreferenceDialog {

	public FilterPreferenceDialog(final Shell parentShell, final PreferenceManager manager) {
		super(parentShell, manager);
	}

	@Override
	protected Control createTreeAreaContents(final Composite parent) {
		final Composite treeArea = new Composite(parent, SWT.NONE);
		treeArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.horizontalSpacing = 0;
		treeArea.setLayout(gridLayout);
		final Text filterText = new Text(treeArea, SWT.SEARCH);
		filterText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		final Control control = super.createTreeAreaContents(treeArea);
		treeArea.setBackground(control.getBackground());
		return treeArea;
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
}
