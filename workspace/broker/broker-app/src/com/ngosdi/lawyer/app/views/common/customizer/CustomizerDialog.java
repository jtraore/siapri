package com.ngosdi.lawyer.app.views.common.customizer;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class CustomizerDialog extends DialogBox implements ICustomizerStateListener {

	private final ICustomizer<?> customizer;
	private final DocumentList documentList;

	public CustomizerDialog(final Shell parentShell, final ICustomizer<?> customizer) {
		this(parentShell, customizer, null);
	}

	public CustomizerDialog(final Shell parentShell, final ICustomizer<?> customizer, final DocumentList documentList) {
		super(parentShell);
		this.customizer = customizer;
		this.customizer.addCustomizerStateListener(this);
		this.documentList = documentList;
		// TODO Remove the listener
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		if (documentList == null) {
			return createControl(parent);
		}
		return createTabControl(parent);
	}

	private Control createControl(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = customizer.createArea(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		return area;
	}

	private Control createTabControl(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);

		final CTabFolder tabFolder = new CTabFolder(area, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite customizerContainer = customizer.createArea(tabFolder, SWT.NONE);
		customizerContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		final CTabItem generalItem = new CTabItem(tabFolder, SWT.NONE);
		generalItem.setText("Général");
		generalItem.setControl(customizerContainer);

		final Composite docListComposite = documentList.createComposite(tabFolder, SWT.NONE);
		final CTabItem documentsItem = new CTabItem(tabFolder, SWT.NONE);
		documentsItem.setText("Documents");
		documentsItem.setControl(docListComposite);
		docListComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		return area;
	}

	@Override
	public void create() {
		super.create();
		setTitle(customizer.getTitle());
		setMessage(customizer.getDescription(), IMessageProvider.INFORMATION);
	}

	@Override
	protected Button createButton(final Composite parent, final int id, final String label, final boolean defaultButton) {
		final Button button = super.createButton(parent, id, label, defaultButton);
		if (id == OK) {
			button.setEnabled(customizer.getValidationStatus().isOK());
		}
		return button;
	}

	@Override
	public void customizerStateChanged(final CustomizerStateEvent event) {
		final Button button = getButton(OK);
		if (button != null) {
			button.setEnabled(event.getStatus().isOK());
		}
	}

	@Override
	protected void okPressed() {
		customizer.validateUpdate();
		if (documentList != null) {
			documentList.validateUpdate();
		}
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		customizer.cancelUpdate();
		super.cancelPressed();
	}
}
