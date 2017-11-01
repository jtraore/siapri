package com.siapri.broker.app.views.common.customizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddressComposite extends Composite {

	public AddressComposite(final Composite parent) {
		super(parent, SWT.NONE);
		buildUI();
	}
	
	private void buildUI() {
		
		final GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);
		
		final Label streetNumberLabel = new Label(this, SWT.NONE);
		streetNumberLabel.setText("Numéro: ");
		final Text streetNumerText = new Text(this, SWT.BORDER);
		
		final Label streetLabel = new Label(this, SWT.NONE);
		streetLabel.setText("Rue: ");
		final Text streetText = new Text(this, SWT.BORDER);
		
		final Label zipCodeLabel = new Label(this, SWT.NONE);
		zipCodeLabel.setText("Code postal: ");
		final Text zipCodeText = new Text(this, SWT.BORDER);
		
		final Label cityLabel = new Label(this, SWT.NONE);
		cityLabel.setText("Rue: ");
		final Text cityText = new Text(this, SWT.BORDER);
		
		final Label countryLabel = new Label(this, SWT.NONE);
		countryLabel.setText("Rue: ");
		final Text countryText = new Text(this, SWT.BORDER);
	}

}
