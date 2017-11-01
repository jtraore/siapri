package com.siapri.broker.app.views.common.customizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.customizer.databinding.BindingSupport;

public final class CustomizerUtil {
	
	private CustomizerUtil() {
	}
	
	public static void setUpAddressComposite(final Composite parent, final AbstractCustomizerModel<?> customizerModel, final BindingSupport bindingSupport, final String addressProperty, final boolean validate) {
		final Label streetNumberLabel = new Label(parent, SWT.NONE);
		streetNumberLabel.setText("Numéro: ");
		final Text streetNumerText = new Text(parent, SWT.BORDER);
		streetNumerText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 1, 1));
		
		final Label streetLabel = new Label(parent, SWT.NONE);
		streetLabel.setText("Rue: ");
		final Text streetText = new Text(parent, SWT.BORDER);
		streetText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 3, 1));

		final Label zipCodeLabel = new Label(parent, SWT.NONE);
		zipCodeLabel.setText("Code postal: ");
		final Text zipCodeText = new Text(parent, SWT.BORDER);
		zipCodeText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false));

		final Label cityLabel = new Label(parent, SWT.NONE);
		cityLabel.setText("Ville: ");
		final Text cityText = new Text(parent, SWT.BORDER);
		cityText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 3, 1));

		final Label countryLabel = new Label(parent, SWT.NONE);
		countryLabel.setText("Pays: ");
		final Text countryText = new Text(parent, SWT.BORDER);
		countryText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 5, 1));

		if (validate) {
			bindingSupport.bindText(customizerModel, addressProperty + ".number", streetNumerText, IValidationSupport.NON_EMPTY_VALIDATOR);
			bindingSupport.bindText(customizerModel, addressProperty + ".street", streetText, IValidationSupport.NON_EMPTY_VALIDATOR);
			bindingSupport.bindText(customizerModel, addressProperty + ".postalCode", zipCodeText, IValidationSupport.NON_EMPTY_VALIDATOR);
			bindingSupport.bindText(customizerModel, addressProperty + ".city", cityText, IValidationSupport.NON_EMPTY_VALIDATOR);
			bindingSupport.bindText(customizerModel, addressProperty + ".country", countryText, IValidationSupport.NON_EMPTY_VALIDATOR);
		} else {
			bindingSupport.bindText(customizerModel, addressProperty + ".number", streetNumerText);
			bindingSupport.bindText(customizerModel, addressProperty + ".street", streetText);
			bindingSupport.bindText(customizerModel, addressProperty + ".postalCode", zipCodeText);
			bindingSupport.bindText(customizerModel, addressProperty + ".city", cityText);
			bindingSupport.bindText(customizerModel, addressProperty + ".country", countryText);
		}
	}
	
	public static void setUpAddressComposite(final Composite parent, final AbstractCustomizerModel<?> customizerModel, final BindingSupport bindingSupport, final String addressProperty) {
		setUpAddressComposite(parent, customizerModel, bindingSupport, addressProperty, false);
	}
}
