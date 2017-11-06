package com.siapri.broker.app.views.contract;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.client.ClientDataListModel;
import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.customizer.ObjectSeekComposite;
import com.siapri.broker.app.views.common.customizer.SearchContext;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.app.views.insurancetype.InsuranceTypeDataListModel;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;

public class ContractCustomizer extends AbstractCustomizer<Contract> {
	
	private final ContractCustomizerModel customizerModel;

	public ContractCustomizer(final Contract contract, final String title, final String description) {
		super(contract, title, description);
		customizerModel = ProxyFactory.createProxy(new ContractCustomizerModel(contract));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);
		
		final Label numberLabel = new Label(composite, SWT.NONE);
		numberLabel.setText("Number: ");
		final Text codeText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "number", codeText, IValidationSupport.NON_EMPTY_VALIDATOR);
		codeText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));
		
		// new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));

		final Label subscriptionDateLabel = new Label(composite, SWT.NONE);
		subscriptionDateLabel.setText("Date de souscription: ");
		final DateTime subscriptionDateField = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		subscriptionDateField.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		subscriptionDateField.setLayoutData(gridData);
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "subscriptionDate", subscriptionDateField, IValidationSupport.NON_EMPTY_VALIDATOR);

		final Label clientLabel = new Label(composite, SWT.NONE);
		clientLabel.setText("Client:");

		final LabelProvider clientLabelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof Person) {
					final Person person = (Person) element;
					return String.format("%s %s", person.getFirstName(), person.getLastName());
				}
				final Company company = (Company) element;
				return String.format("%s - %s", company.getSiret(), company.getName());
			}
		};
		final DataListModel clientListModel = new ClientDataListModel(parent);
		final SearchContext cleintSearchContext = new SearchContext(clientListModel, clientLabelProvider, "Recherche client", "Cette fenetre permet de rechercher un client");
		final ObjectSeekComposite clientSeekComposite = new ObjectSeekComposite(composite, cleintSearchContext);
		clientSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "client", clientSeekComposite, IValidationSupport.NON_EMPTY_VALIDATOR);

		new TitledSeparator(composite, "Type d'assurance et garanties").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final Label insuranceTypeLabel = new Label(composite, SWT.NONE);
		insuranceTypeLabel.setText("Type d'assurance:");

		final LabelProvider insuranceTypeLabelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final InsuranceType insuranceType = (InsuranceType) element;
				return insuranceType.getCode();
			}
		};
		final DataListModel insuranceTypeListModel = new InsuranceTypeDataListModel(parent);
		final SearchContext insuranceTypeSearchContext = new SearchContext(insuranceTypeListModel, insuranceTypeLabelProvider, "Recherche type d'assurance", "Cette fenetre permet de rechercher un type d'assurance");
		final ObjectSeekComposite insuranceTypeComposite = new ObjectSeekComposite(composite, insuranceTypeSearchContext);
		clientSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "insuranceType", insuranceTypeComposite, IValidationSupport.NON_EMPTY_VALIDATOR);

		// final WarrantyDataListModel dataListModel = new WarrantyDataListModel(composite, object);
		// final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		// final GridData warrantiGridData = new GridData(GridData.FILL, GridData.FILL, true, true, 6, 1);
		// warrantiGridData.heightHint = 500;
		// dataListComposite.setLayoutData(warrantiGridData);
		
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
