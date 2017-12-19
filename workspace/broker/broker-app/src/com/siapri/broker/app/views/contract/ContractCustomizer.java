package com.siapri.broker.app.views.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
import com.siapri.broker.app.views.common.proxy.IProxy;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.app.views.company.CompanyDataListModel;
import com.siapri.broker.app.views.contract.ContractCustomizerModel.SubjectAttributeValue;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Warranty;
import com.siapri.broker.business.model.WarrantyFormula;

public class ContractCustomizer extends AbstractCustomizer<Contract> {

	private final ContractCustomizerModel customizerModel;
	private final List<InsuranceType> insuranceTypes;
	private final List<Company> insurers;
	
	public ContractCustomizer(final Contract contract, final List<Company> insurers, final List<InsuranceType> insuranceTypes, final String title, final String description) {
		super(contract, title, description);
		final WarrantyFormula formula = contract.getWarrantyFormula();
		InsuranceType insuranceType = null;
		if (formula != null) {
			insuranceType = insuranceTypes.stream().filter(it -> it.getFormulas().contains(formula)).findFirst().get();
		}
		customizerModel = ProxyFactory.createProxy(new ContractCustomizerModel(contract, insuranceType));
		this.insurers = insurers;
		this.insuranceTypes = insuranceTypes;
	}
	
	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);

		final Button signedButton = new Button(composite, SWT.CHECK);
		signedButton.setText("Contrat signé");
		signedButton.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		bindingSupport.bindCheckBox(customizerModel, "signed", signedButton);

		final Label numberLabel = new Label(composite, SWT.NONE);
		numberLabel.setText("Number: ");
		final Text numberText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "number", numberText, IValidationSupport.NON_EMPTY_VALIDATOR);
		numberText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));

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
		final ClientDataListModel clientListModel = new ClientDataListModel(parent);
		clientListModel.setTitle("Particuliers");
		final CompanyDataListModel entrpriseListModel = new CompanyDataListModel(composite, false);
		entrpriseListModel.setTitle("Entreprises");
		final SearchContext clientSearchContext = new SearchContext(Arrays.asList(clientListModel, entrpriseListModel), Client.class, clientLabelProvider, "Recherche client", "Cette fenetre permet de rechercher un client");
		final ObjectSeekComposite clientSeekComposite = new ObjectSeekComposite(composite, clientSearchContext);
		clientSeekComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		bindingSupport.bindObjectSeekComposite(customizerModel, "client", clientSeekComposite, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		final Label insurerLabel = new Label(composite, SWT.NONE);
		insurerLabel.setText("Assureur:");
		
		final ComboViewer insurerComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		insurerComboViewer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		insurerComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		insurerComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((Company) element).getName();
			}
		});
		insurerComboViewer.setInput(insurers.toArray());
		bindingSupport.bindComboViewer(customizerModel, "insurer", insurerComboViewer, IValidationSupport.NON_EMPTY_VALIDATOR);
		
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
		
		new TitledSeparator(composite, "Liste des garanties").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));
		new TitledSeparator(composite, "Propriété de l'objet assuré").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));
		
		final Composite warrantyTableComposite = new Composite(composite, SWT.NONE);
		final GridData wrrantyGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		warrantyTableComposite.setLayoutData(wrrantyGridData);
		warrantyTableComposite.setLayout(new FillLayout());
		wrrantyGridData.heightHint = 500;
		createWarrantyTable(warrantyTableComposite);

		final Composite attributeTableComposite = new Composite(composite, SWT.NONE);
		final GridData attributeGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		attributeTableComposite.setLayoutData(attributeGridData);
		attributeTableComposite.setLayout(new FillLayout());
		attributeGridData.heightHint = 500;
		createSubjectAttributesTable(attributeTableComposite);

		return composite;
	}

	private void createWarrantyTable(final Composite parent) {
		
		final TableViewer tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		table.setLayout(new TableLayout());
		
		tableViewer.setContentProvider(new ObservableListContentProvider());
		
		final TableViewerColumn columnName = new TableViewerColumn(tableViewer, SWT.NONE);
		columnName.getColumn().setText("Garanties");
		columnName.getColumn().setWidth(350);
		
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

	private void createSubjectAttributesTable(final Composite parent) {
		
		final TableViewer tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		
		tableViewer.setContentProvider(new ObservableListContentProvider());
		
		final TableViewerColumn columnAttribute = new TableViewerColumn(tableViewer, SWT.NONE);
		columnAttribute.getColumn().setText("Proprités");
		columnAttribute.getColumn().setWidth(350);
		
		final TableViewerColumn columnValue = new TableViewerColumn(tableViewer, SWT.NONE);
		columnValue.getColumn().setText("Valeur");
		columnValue.getColumn().setWidth(250);
		columnValue.setEditingSupport(new AttributeValueEditingSupport(columnValue.getViewer()));
		
		tableViewer.setLabelProvider(new AttributeLabelProvider());
		final WritableList<SubjectAttributeValue> writableList = new WritableList<>(new ArrayList<>(), SubjectAttributeValue.class);
		if (customizerModel.getInsuranceType() != null) {
			writableList.addAll(customizerModel.getAttributeValues());
		}
		tableViewer.setInput(writableList);
		
		((IProxy) customizerModel).addPropertyChangeListener(event -> {
			if (event.getPropertyName().equals("insuranceType")) {
				writableList.clear();
				writableList.addAll(customizerModel.getAttributeValues());
				tableViewer.refresh();
			}
		});
	}
	
	private class AttributeValueEditingSupport extends EditingSupport {
		
		public AttributeValueEditingSupport(final ColumnViewer viewer) {
			super(viewer);
		}
		
		@Override
		protected boolean canEdit(final Object arg0) {
			return true;
		}
		
		@Override
		protected CellEditor getCellEditor(final Object arg0) {
			return new TextCellEditor(((TableViewer) getViewer()).getTable());
		}
		
		@Override
		protected Object getValue(final Object obj) {
			final SubjectAttributeValue attributeValue = (SubjectAttributeValue) obj;
			return attributeValue.getValue() != null ? attributeValue.getValue() : "";
		}
		
		@Override
		protected void setValue(final Object element, final Object value) {
			((SubjectAttributeValue) element).setValue(String.valueOf(value));
			getViewer().update(element, new String[] { "value" });
			((TableViewer) getViewer()).getTable().layout(true);
		}
	}
	
	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}
	
	@Override
	public void cancelUpdate() {
	}
	
	private final class AttributeLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		@Override
		public Image getColumnImage(final Object object, final int column) {
			return null;
		}
		
		@Override
		public String getColumnText(final Object object, final int column) {
			final SubjectAttributeValue attributeValue = (SubjectAttributeValue) object;
			switch (column) {
				case 0:
					return attributeValue.getAttribute().getDescription();
				case 1:
					return attributeValue.getValue();
			}
			return null;
		}
	}

}
