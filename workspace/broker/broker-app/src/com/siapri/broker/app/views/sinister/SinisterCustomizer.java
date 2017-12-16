package com.siapri.broker.app.views.sinister;

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
import com.siapri.broker.app.views.common.customizer.ObjectSeekComposite;
import com.siapri.broker.app.views.common.customizer.SearchContext;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.app.views.contract.ContractDataListModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;

public class SinisterCustomizer extends AbstractCustomizer<Sinister> {
	
	private final SinisterCustomizerModel customizerModel;
	
	public SinisterCustomizer(final Sinister sinister, final String title, final String description) {
		super(sinister, title, description);
		customizerModel = ProxyFactory.createProxy(new SinisterCustomizerModel(sinister));
	}
	
	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);

		final Label numberLabel = new Label(composite, SWT.NONE);
		numberLabel.setText("Number: ");
		final Text numberText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "number", numberText, IValidationSupport.NON_EMPTY_VALIDATOR);
		numberText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, false, false, 2, 1));

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));
		
		final Label contractLabel = new Label(composite, SWT.NONE);
		contractLabel.setText("Contrat:");
		
		final LabelProvider contractLabelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof Contract) {
					final Contract contract = (Contract) element;
					final Client client = contract.getClient();
					if (client instanceof Person) {
						return String.format("%s - %s %s", contract.getNumber(), ((Person) client).getFirstName(), ((Person) client).getLastName());
					}
					return String.format("%s - %s", contract.getNumber(), ((Company) client).getName());
				}
				return "";
			}
		};
		final DataListModel contractListModel = new ContractDataListModel(parent);
		final SearchContext contractSearchContext = new SearchContext(contractListModel, contractLabelProvider, "Recherche contrat", "Cette fenetre permet de rechercher un contrat");
		final ObjectSeekComposite contractSeekComposite = new ObjectSeekComposite(composite, contractSearchContext);
		contractSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "contract", contractSeekComposite, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		final Label occuredDateLabel = new Label(composite, SWT.NONE);
		occuredDateLabel.setText("Date de l'événement");
		final DateTime occuredDateField = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		occuredDateField.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final GridData gridData = new GridData(GridData.BEGINNING);
		gridData.widthHint = 150;
		occuredDateField.setLayoutData(gridData);
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "occuredDate", occuredDateField, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		// Filler
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 4, 1));
		
		final Label descriptionLabel = new Label(composite, SWT.NONE);
		descriptionLabel.setText("Description");
		final Text descriptionText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "description", descriptionText, IValidationSupport.NON_EMPTY_VALIDATOR);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		
		new TitledSeparator(composite, "Adresse").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		
		CustomizerUtil.setUpAddressComposite(composite, customizerModel, bindingSupport, "address", true);
		
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
