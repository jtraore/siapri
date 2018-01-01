package com.siapri.broker.app.views.insurancetype;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.WarrantyFormula;

public class FormulaDataListModel extends DataListModel<WarrantyFormula> {

	private final InsuranceType insuranceType;

	public FormulaDataListModel(final Composite parent, final InsuranceType insuranceType) {
		super(false);
		this.insuranceType = insuranceType;
		initialize();
	}

	@Override
	protected void initialize() {

		super.initialize();

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "code", "name" };

		columnDescriptors = new ColumnDescriptor[2];
		columnDescriptors[0] = new ColumnDescriptor("Code", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Name", 0.70, 125);
	}

	@Override
	protected WarrantyFormula save(final WarrantyFormula element) {
		final List<WarrantyFormula> formulas = insuranceType.getFormulas();
		if (!formulas.contains(element)) {
			formulas.add(element);
		}
		return element;
	}
	
	@Override
	protected void delete(final WarrantyFormula element) {
		insuranceType.getFormulas().remove(element);
	}

	@Override
	protected ICustomizer<WarrantyFormula> createCustomizer(final WarrantyFormula element, final String title, final String description) {
		return new FormulaCustomizer(element, insuranceType, title, description);
	}
	
	@Override
	protected List<WarrantyFormula> loadElements() {
		return insuranceType.getFormulas();
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final WarrantyFormula formula = (WarrantyFormula) object;
			switch (column) {
				case 0:
					return formula.getCode();
				case 1:
					return formula.getName();
			}
			return null;
		}
	}
}
