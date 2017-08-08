package com.ngosdi.lawyer.app.views.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractFieldEditorPreferencePage extends FieldEditorPreferencePage {

	public AbstractFieldEditorPreferencePage(final String title) {
		super(title, GRID);
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		getApplyButton().setText("Appliquer");
		final Button defaultsButton = getDefaultsButton();
		defaultsButton.setText("Restaurer par défaut");
		((GridData) defaultsButton.getLayoutData()).widthHint = defaultsButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	@Override
	public boolean performOk() {
		super.performOk();
		final AppPreferenceStore store = (AppPreferenceStore) getPreferenceStore();
		// The preference store is null if the page is not opened
		if (store != null) {
			store.saveAll();
		}
		return true;
	}
}
