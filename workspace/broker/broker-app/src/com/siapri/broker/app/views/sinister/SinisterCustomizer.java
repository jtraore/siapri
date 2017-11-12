package com.siapri.broker.app.views.sinister;

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
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Sinister;

public class SinisterCustomizer extends AbstractCustomizer<Sinister> {
	
	private final SinisterCustomizerModel customizerModel;
	
	public SinisterCustomizer(final Sinister sinister, final String title, final String description, final Contract contract) {
		super(sinister, title, description);
		customizerModel = ProxyFactory.createProxy(new SinisterCustomizerModel(sinister, contract));
	}
	
	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(4, false);
		composite.setLayout(gridLayout);
		
		// TODO
		// SearchContext searchContext = new
		// ObjectSeekComposite contractComposite = new ObjectSeekComposite(composite, searchContext)
		
		final Label clientLabel = new Label(composite, SWT.NONE);
		clientLabel.setText("Client");
		
		final Label clientLabelValue = new Label(composite, SWT.NONE);
		clientLabelValue.setText("clientLabelValue");

		// Filler
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		
		final Label contractLabel = new Label(composite, SWT.NONE);
		contractLabel.setText("Contrat");
		final Text contractText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "contract", contractText, IValidationSupport.NON_EMPTY_VALIDATOR);
		contractText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		// Filler
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		
		final Label occuredDateLabel = new Label(composite, SWT.NONE);
		occuredDateLabel.setText("Date de l'événement");
		final DateTime occuredDateField = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		occuredDateField.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		occuredDateField.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "occuredDate", occuredDateField, IValidationSupport.NON_EMPTY_VALIDATOR);

		// Filler
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		
		final Label descriptionLabel = new Label(composite, SWT.NONE);
		descriptionLabel.setText("Description");
		final Text descriptionText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "description", descriptionText, IValidationSupport.NON_EMPTY_VALIDATOR);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));
		
		new TitledSeparator(composite, "Adresse").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));
		
		final Label addressNumberLabel = new Label(composite, SWT.NONE);
		addressNumberLabel.setText("Numéro");
		final Text addressNumberText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "number", addressNumberText);
		addressNumberText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		final Label addressStreetLabel = new Label(composite, SWT.NONE);
		addressStreetLabel.setText("Rue");
		final Text addressStreetText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "street", addressStreetText);
		addressStreetText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final Label addressAdditionalInfoLabel = new Label(composite, SWT.NONE);
		addressAdditionalInfoLabel.setText("Complément d'adresse");
		final Text addressAdditionalInfoText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "additionalInfo", addressAdditionalInfoText);
		addressAdditionalInfoText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final Label addressPostalCodeLabel = new Label(composite, SWT.NONE);
		addressPostalCodeLabel.setText("Code postal");
		final Text addressPostalCodeText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "postalCode", addressPostalCodeText);
		addressPostalCodeText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final Label addressCityLabel = new Label(composite, SWT.NONE);
		addressCityLabel.setText("Ville");
		final Text addressCityText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "city", addressCityText, IValidationSupport.NON_EMPTY_VALIDATOR);
		addressCityText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final Label addressCountryLabel = new Label(composite, SWT.NONE);
		addressCountryLabel.setText("Pays");
		final Text addressCountryText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "country", addressCountryText, IValidationSupport.NON_EMPTY_VALIDATOR);
		addressCountryText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		return composite;
	}
	
	@Override
	public void validateUpdate() {
		customizerModel.validate();

	}
	
	@Override
	public void cancelUpdate() {
		// TODO Auto-generated method stub

	}

}
