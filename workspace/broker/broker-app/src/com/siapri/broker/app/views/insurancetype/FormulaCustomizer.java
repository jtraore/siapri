package com.siapri.broker.app.views.insurancetype;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.app.views.insurancetype.FormulaCustomizerModel.WarrantySelection;
import com.siapri.broker.business.model.Document;
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

		final Composite warrantySelectionTableComposite = new Composite(parent, SWT.NONE);
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1);
		warrantySelectionTableComposite.setLayoutData(gridData);
		gridData.heightHint = 300;
		createWarrantySelectionTable(warrantySelectionTableComposite);
		
		return composite;
	}

	private void createWarrantySelectionTable(final Composite parent) {
		final TableViewer documentViewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		final Table table = documentViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		final ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		documentViewer.setContentProvider(contentProvider);
		documentViewer.getControl().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		final TableViewerColumn columnPath = new TableViewerColumn(documentViewer, SWT.NONE);
		columnPath.getColumn().setText("Sélection");
		columnPath.getColumn().setWidth(50);

		final TableViewerColumn columnDescription = new TableViewerColumn(documentViewer, SWT.NONE);
		columnDescription.getColumn().setText("Libellé");
		columnDescription.getColumn().setWidth(300);
		// columnDescription.setEditingSupport(new DescriptionEditingSupport(columnDescription.getViewer()));

		documentViewer.setLabelProvider(new WarrantyListLabelProvider());
		documentViewer.setInput(new WritableList<>(customizerModel.getWarrantySelections(), WarrantySelection.class));
	}

	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}

	@Override
	public void cancelUpdate() {
	}

	private final class WarrantyListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object object, final int column) {
			if (column == 0) {
				return Util.getDefaultProgramImage((Document) object);
			}
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Document document = (Document) object;
			switch (column) {
				case 0:
					return document.getPath();
				case 1:
					return document.getDescription();
			}
			return null;
		}
	}
	
}
