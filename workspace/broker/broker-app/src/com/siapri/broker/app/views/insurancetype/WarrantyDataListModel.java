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
import com.siapri.broker.business.model.Warranty;

public class WarrantyDataListModel extends DataListModel<Warranty> {

	private final InsuranceType insuranceType;

	public WarrantyDataListModel(final Composite parent, final InsuranceType insuranceType) {
		super(false);
		this.insuranceType = insuranceType;
		initialize();
	}

	@Override
	protected void initialize() {

		super.initialize();

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "code", "description" };

		columnDescriptors = new ColumnDescriptor[2];
		columnDescriptors[0] = new ColumnDescriptor("Code", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Description", 0.70, 125);
	}

	@Override
	protected List<Warranty> loadElements() {
		return insuranceType.getWarranties();
	}

	@Override
	protected Warranty save(final Warranty element) {
		final List<Warranty> warranties = insuranceType.getWarranties();
		if (!warranties.contains(element)) {
			warranties.add(element);
		}
		return element;
	}

	@Override
	protected void delete(final Warranty element) {
		insuranceType.getWarranties().remove(element);
		// Update the code from all the warranty formulas it used
		insuranceType.getFormulas().forEach(formula -> formula.getWarrantyCodes().remove(element.getCode()));
	}

	@Override
	protected ICustomizer<Warranty> createCustomizer(final Warranty element, final String title, final String description) {
		return new CodeDescriptionPairCustomizer<>(element, title, description);
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Warranty warranty = (Warranty) object;
			switch (column) {
				case 0:
					return warranty.getCode();
				case 1:
					return warranty.getDescription();
			}
			return null;
		}
	}
}
