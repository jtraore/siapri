package com.siapri.broker.app.views.contract;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.client.ClientDataListModel;
import com.siapri.broker.app.views.common.EImage;
import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.customizer.ObjectSeekComposite;
import com.siapri.broker.app.views.common.customizer.SearchContext;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.app.views.common.proxy.IProxy;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Warranty;
import com.siapri.broker.business.model.WarrantyFormula;

public class ContractCustomizer extends AbstractCustomizer<Contract> {

	private final ContractCustomizerModel customizerModel;
	private final List<InsuranceType> insuranceTypes;
	
	public ContractCustomizer(final Contract contract, final List<InsuranceType> insuranceTypes, final String title, final String description) {
		super(contract, title, description);
		final WarrantyFormula formula = contract.getWarrantyFormula();
		InsuranceType insuranceType = null;
		if (formula != null) {
			insuranceType = insuranceTypes.stream().filter(it -> it.getFormulas().contains(formula)).findFirst().get();
		}
		customizerModel = ProxyFactory.createProxy(new ContractCustomizerModel(contract, insuranceType));
		this.insuranceTypes = insuranceTypes;
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
		final SearchContext clientSearchContext = new SearchContext(clientListModel, clientLabelProvider, "Recherche client", "Cette fenetre permet de rechercher un client");
		final ObjectSeekComposite clientSeekComposite = new ObjectSeekComposite(composite, clientSearchContext);
		clientSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "client", clientSeekComposite, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		new TitledSeparator(composite, "Type d'assurance et garanties").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		
		final Label insuranceTypeLabel = new Label(composite, SWT.NONE);
		insuranceTypeLabel.setText("Type d'assurance: ");

		final ComboViewer insuranceTypeComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		insuranceTypeComboViewer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		insuranceTypeComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		insuranceTypeComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((InsuranceType) element).getName();
			}
		});
		insuranceTypeComboViewer.setInput(insuranceTypes.toArray());
		bindingSupport.bindComboViewer(customizerModel, "insuranceType", insuranceTypeComboViewer, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		final Label formulaLabel = new Label(composite, SWT.NONE);
		formulaLabel.setText("Formule de garanties: ");

		final ComboViewer formulaComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		formulaComboViewer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		formulaComboViewer.setContentProvider(new ObservableListContentProvider());
		formulaComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((WarrantyFormula) element).getName();
			}
		});
		final WritableList<Object> formulaList = new WritableList<>(new ArrayList<>(), WarrantyFormula.class);
		formulaComboViewer.setInput(formulaList);
		if (customizerModel.getInsuranceType() != null) {
			formulaList.addAll(customizerModel.getInsuranceType().getFormulas());
		}
		bindingSupport.bindComboViewer(customizerModel, "warrantyFormula", formulaComboViewer, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		((IProxy) customizerModel).addPropertyChangeListener(event -> {
			if (event.getPropertyName().equals("insuranceType")) {
				formulaList.clear();
				formulaList.addAll(((InsuranceType) event.getNewValue()).getFormulas());
				formulaComboViewer.refresh();
			}
		});
		
		final Composite warrantyTableComposite = new Composite(composite, SWT.NONE);
		final GridData wrrantyGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1);
		warrantyTableComposite.setLayoutData(wrrantyGridData);
		warrantyTableComposite.setLayout(new FillLayout());
		wrrantyGridData.heightHint = 300;
		createWarrantyTable(warrantyTableComposite);

		return composite;
	}

	private void createWarrantyTable(final Composite parent) {
		
		final TableViewer tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		
		tableViewer.setContentProvider(new ObservableListContentProvider());
		
		final TableViewerColumn columnName = new TableViewerColumn(tableViewer, SWT.NONE);
		columnName.getColumn().setText("Garanties");
		columnName.getColumn().setWidth(700);
		
		tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				final WarrantyFormula formula = customizerModel.getWarrantyFormula();
				if (formula != null) {
					if (formula.getWarrantyCodes().contains(((Warranty) element).getCode())) {
						return EImage.OK.getSwtImage();
					}
				}
				return null;
			}

			@Override
			public final String getText(final Object element) {
				return super.getText(((Warranty) element).getDescription());
			}
		});
		final WritableList<Warranty> writableList = new WritableList<>(new ArrayList<>(), Warranty.class);
		if (customizerModel.getInsuranceType() != null) {
			writableList.addAll(customizerModel.getInsuranceType().getWarranties());
		}
		tableViewer.setInput(writableList);
		
		((IProxy) customizerModel).addPropertyChangeListener(event -> {
			if (event.getPropertyName().equals("insuranceType")) {
				writableList.clear();
				writableList.addAll(((InsuranceType) event.getNewValue()).getWarranties());
				tableViewer.refresh();
			} else if (event.getPropertyName().equals("warrantyFormula")) {
				tableViewer.refresh();
			}
		});
	}
	
	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}
	
	@Override
	public void cancelUpdate() {
	}

}
