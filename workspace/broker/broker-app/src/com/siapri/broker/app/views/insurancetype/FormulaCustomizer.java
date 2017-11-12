package com.siapri.broker.app.views.insurancetype;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.app.views.insurancetype.FormulaCustomizerModel.WarrantySelection;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.WarrantyFormula;

public class FormulaCustomizer extends AbstractCustomizer<WarrantyFormula> {

	private final FormulaCustomizerModel customizerModel;
	
	public FormulaCustomizer(final WarrantyFormula formula, final InsuranceType insuranceType, final String title, final String description) {
		super(formula, title, description);
		customizerModel = ProxyFactory.createProxy(new FormulaCustomizerModel(formula, insuranceType));
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
		descriptionLabel.setText("Name: ");
		descriptionLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false));
		final Text descriptionText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "name", descriptionText, IValidationSupport.NON_EMPTY_VALIDATOR);
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		
		new TitledSeparator(composite, "Sélection des garanties").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));
		
		final Composite warrantySelectionTableComposite = new Composite(composite, SWT.NONE);
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1);
		warrantySelectionTableComposite.setLayoutData(gridData);
		warrantySelectionTableComposite.setLayout(new FillLayout());
		gridData.heightHint = 300;
		createWarrantySelectionTable(warrantySelectionTableComposite);

		return composite;
	}
	
	private void createWarrantySelectionTable(final Composite parent) {

		final CheckboxTableViewer tableViewer = CheckboxTableViewer.newCheckList(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		tableViewer.setContentProvider(new ObservableListContentProvider());

		final TableViewerColumn columnSelection = new TableViewerColumn(tableViewer, SWT.NONE);
		columnSelection.getColumn().setText("Garanties");
		columnSelection.getColumn().setWidth(700);
		
		tableViewer.setCheckStateProvider(new ICheckStateProvider() {
			
			@Override
			public boolean isGrayed(final Object element) {
				return false;
			}
			
			@Override
			public boolean isChecked(final Object element) {
				return ((WarrantySelection) element).isSelected();
			}
		});

		tableViewer.addCheckStateListener(event -> ((WarrantySelection) event.getElement()).setSelected(event.getChecked()));

		tableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public final String getText(final Object element) {
				return super.getText(((WarrantySelection) element).getWarranty().getDescription());
			}
		});
		tableViewer.setInput(new WritableList<>(customizerModel.getWarrantySelections(), WarrantySelection.class));
	}
	
	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}
	
	@Override
	public void cancelUpdate() {
	}

}
