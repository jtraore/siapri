package com.siapri.broker.app.views.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TitledSeparator extends Composite {

	public TitledSeparator(final Composite parent, final String title) {
		this(parent, title, null);
	}

	public TitledSeparator(final Composite parent, final String title, final Image image) {
		super(parent, SWT.NONE);
		buildUI(title, image);
	}

	private void buildUI(final String title, final Image image) {

		final GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);

		final CLabel label = new CLabel(this, SWT.NONE);
		label.setText(title);
		label.setImage(image);
		label.setData("org.eclipse.e4.ui.css.id", "TitledSeparatorLabelStyle");

		final Label separator = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		final GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}
}
