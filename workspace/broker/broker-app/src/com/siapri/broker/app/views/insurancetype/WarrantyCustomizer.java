package com.siapri.broker.app.views.insurancetype;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Warranty;

public class WarrantyCustomizer extends AbstractCustomizer<Warranty> {
	
	private final WarrantyCustomizerModel customizerModel;

	public WarrantyCustomizer(final Warranty warranty, final String title, final String description) {
		super(warranty, title, description);
		customizerModel = ProxyFactory.createProxy(new WarrantyCustomizerModel(warranty));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);
		
		final Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Nom: ");
		final Text nameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "name", nameText, IValidationSupport.NON_EMPTY_VALIDATOR);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));

		final Label descriptionLabel = new Label(composite, SWT.NONE);
		descriptionLabel.setText("description: ");
		final Text descriptionText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "description", descriptionText, IValidationSupport.NON_EMPTY_VALIDATOR);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		
		return composite;
	}

	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}

	@Override
	public void cancelUpdate() {
	}
	
}
