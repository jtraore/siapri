package com.siapri.broker.app.views.company;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.CustomizerUtil;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Company;

public class CompanyCustomizer extends AbstractCustomizer<Company> {
	
	private final CompanyCustomizerModel customizerModel;
	
	public CompanyCustomizer(final Company company, final String title, final String description) {
		super(company, title, description);
		customizerModel = ProxyFactory.createProxy(new CompanyCustomizerModel(company));
	}
	
	@Override
	public Composite createArea(final Composite parent, final int style) {

		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);
		
		final Label siretLabel = new Label(composite, SWT.NONE);
		siretLabel.setText("Numéro siret: ");
		final Text siretText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "siret", siretText, IValidationSupport.NON_EMPTY_VALIDATOR);
		siretText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 5, 1));
		
		final Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Nom: ");
		final Text nameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "name", nameText, IValidationSupport.NON_EMPTY_VALIDATOR);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));

		final Label activityLabel = new Label(composite, SWT.NONE);
		activityLabel.setText("Activité: ");
		final Text activityText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "activity", activityText, IValidationSupport.NON_EMPTY_VALIDATOR);
		activityText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		
		new TitledSeparator(composite, "Adresse postale").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		CustomizerUtil.setUpAddressComposite(composite, customizerModel, bindingSupport, "address", true);
		
		new TitledSeparator(composite, "Numéros téléphone/fax").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		
		final Label landLabel = new Label(composite, SWT.NONE);
		landLabel.setText("Fixe: ");
		final Text landText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "landPhone", landText, IValidationSupport.NON_EMPTY_VALIDATOR);
		landText.setLayoutData(gridData);
		
		final Label mobileLabel = new Label(composite, SWT.NONE);
		mobileLabel.setText("Mobile: ");
		final Text mobileText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "mobilePhone", mobileText);
		mobileText.setLayoutData(gridData);

		final Label faxLabel = new Label(composite, SWT.NONE);
		faxLabel.setText("Fax: ");
		final Text faxText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "fax", faxText);
		faxText.setLayoutData(gridData);
		
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
