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
import com.siapri.broker.business.model.ICodeDescriptionPair;

public class CodeDescriptionPairCustomizer<T extends ICodeDescriptionPair> extends AbstractCustomizer<T> {
	
	private final CodeDescriptionPairCustomizerModel<T> customizerModel;

	public CodeDescriptionPairCustomizer(final T element, final String title, final String description) {
		super(element, title, description);
		customizerModel = ProxyFactory.createProxy(new CodeDescriptionPairCustomizerModel<>(element));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);
		
		final Label codeLabel = new Label(composite, SWT.NONE);
		codeLabel.setText("Code: ");
		final Text codeText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "code", codeText, IValidationSupport.NON_EMPTY_VALIDATOR);
		codeText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));

		final Label descriptionLabel = new Label(composite, SWT.NONE);
		descriptionLabel.setText("description: ");
		descriptionLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false));
		final Text descriptionText = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		bindingSupport.bindText(customizerModel, "description", descriptionText, IValidationSupport.NON_EMPTY_VALIDATOR);
		final GridData descriptionTextGridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1);
		descriptionTextGridData.heightHint = 120;
		descriptionText.setLayoutData(descriptionTextGridData);
		
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
