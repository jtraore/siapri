package com.siapri.broker.app.views.broker;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Broker;
import com.siapri.broker.business.security.Profile;

public class BrokerCustomizer extends AbstractCustomizer<Broker> {

	private final BrokerCustomizerModel customizerModel;

	public BrokerCustomizer(final Broker broker, final String title, final String description) {
		super(broker, title, description);
		customizerModel = ProxyFactory.createProxy(new BrokerCustomizerModel(broker));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		new TitledSeparator(composite, "Identité").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		final Label firstNameLabel = new Label(composite, SWT.NONE);
		firstNameLabel.setText("Nom: ");
		final Text firstNameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "firstName", firstNameText, IValidationSupport.NON_EMPTY_VALIDATOR);
		firstNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label lastNameLabel = new Label(composite, SWT.NONE);
		lastNameLabel.setText("Prénom: ");
		final Text lastNameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "lastName", lastNameText, IValidationSupport.NON_EMPTY_VALIDATOR);
		lastNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Label phoneLabel = new Label(composite, SWT.NONE);
		phoneLabel.setText("Téléphone: ");
		final Text phoneText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "phone", phoneText, IValidationSupport.NON_EMPTY_VALIDATOR);
		phoneText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Label descriptionLabel = new Label(composite, SWT.NONE);
		descriptionLabel.setText("Description: ");
		final Text descriptionText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "description", descriptionText);
		descriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new TitledSeparator(composite, "Authentification").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));
		
		final Label loginLabel = new Label(composite, SWT.NONE);
		loginLabel.setText("Login: ");
		final Text loginText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "login", loginText, IValidationSupport.NON_EMPTY_VALIDATOR);
		loginText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Label passwordLabel = new Label(composite, SWT.NONE);
		passwordLabel.setText("Mot de passe: ");
		final Text passwordText = new Text(composite, SWT.PASSWORD | SWT.BORDER);
		bindingSupport.bindText(customizerModel, "password", passwordText, IValidationSupport.NON_EMPTY_VALIDATOR);
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label profileLabel = new Label(composite, SWT.NONE);
		profileLabel.setText("Profil: ");
		final ComboViewer profileComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		profileComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		profileComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return Util.getProfileAsString((Profile) element);
			}
		});

		profileComboViewer.setInput(Profile.values());
		bindingSupport.bindComboViewer(customizerModel, "profile", profileComboViewer, IValidationSupport.NON_EMPTY_VALIDATOR);

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
