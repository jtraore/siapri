package com.siapri.broker.app.views.client;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.CustomizerUtil;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Gender;
import com.siapri.broker.business.model.Person;

public class ClientCustomizer extends AbstractCustomizer<Person> {

	private final ClientCustomizerModel customizerModel;

	public ClientCustomizer(final Person client, final String title, final String description) {
		super(client, title, description);
		customizerModel = ProxyFactory.createProxy(new ClientCustomizerModel(client));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);
		
		new TitledSeparator(composite, "Identité").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final Label genderLabel = new Label(composite, SWT.NONE);
		genderLabel.setText("Civilité: ");
		final ComboViewer typeComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		typeComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		typeComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (Gender.MALE.equals(element)) {
					return "Mr";
				}
				return "Mme";
			}
		});
		typeComboViewer.setInput(Gender.values());
		bindingSupport.bindComboViewer(customizerModel, "gender", typeComboViewer, IValidationSupport.NON_EMPTY_VALIDATOR);

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));
		
		final Label firstNameLabel = new Label(composite, SWT.NONE);
		firstNameLabel.setText("Nom: ");
		final Text firstNameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "firstName", firstNameText, IValidationSupport.NON_EMPTY_VALIDATOR);
		firstNameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 5, 1));

		final Label lastNameLabel = new Label(composite, SWT.NONE);
		lastNameLabel.setText("Prénom: ");
		final Text lastNameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "lastName", lastNameText, IValidationSupport.NON_EMPTY_VALIDATOR);
		lastNameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		
		final Label birthdateLabel = new Label(composite, SWT.NONE);
		birthdateLabel.setText("Date naissance: ");
		
		final DateTime birthDateField = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		birthDateField.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		// gridData.widthHint = 150;
		birthDateField.setLayoutData(gridData);
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "birthdate", birthDateField, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));

		new TitledSeparator(composite, "Adresse domicile").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		CustomizerUtil.setUpAddressComposite(composite, customizerModel, bindingSupport, "homeAddress", true);
		
		new TitledSeparator(composite, "Adresse travail").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		CustomizerUtil.setUpAddressComposite(composite, customizerModel, bindingSupport, "workAddress");

		new TitledSeparator(composite, "Numéros téléphone").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		
		final Label mobileLabel = new Label(composite, SWT.NONE);
		mobileLabel.setText("Mobile: ");
		final Text mobileText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "mobilePhone", mobileText, IValidationSupport.NON_EMPTY_VALIDATOR);
		mobileText.setLayoutData(gridData);
		
		final Label landLabel = new Label(composite, SWT.NONE);
		landLabel.setText("Fixe: ");
		final Text landText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "landPhone", landText);
		landText.setLayoutData(gridData);
		
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
		// Ask the user to confirm the cancellation
	}
}
