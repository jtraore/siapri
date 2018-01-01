package com.siapri.broker.app.views.insurancetype;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.InsuranceSubjectAttribute;
import com.siapri.broker.business.model.InsuranceType;

public class InsuranceSubjectAttributesDataListModel extends DataListModel<InsuranceSubjectAttribute> {

	private final InsuranceType insuranceType;

	public InsuranceSubjectAttributesDataListModel(final Composite parent, final InsuranceType insuranceType) {
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
	protected List<InsuranceSubjectAttribute> loadElements() {
		return insuranceType.getAttributes();
	}
	
	@Override
	protected InsuranceSubjectAttribute save(final InsuranceSubjectAttribute element) {
		final List<InsuranceSubjectAttribute> attributes = insuranceType.getAttributes();
		if (!attributes.contains(element)) {
			attributes.add(element);
		}
		return element;
	}
	
	@Override
	protected void delete(final InsuranceSubjectAttribute element) {
		insuranceType.getAttributes().remove(element);
	}
	
	@Override
	protected ICustomizer<InsuranceSubjectAttribute> createCustomizer(final InsuranceSubjectAttribute element, final String title, final String description) {
		return new CodeDescriptionPairCustomizer<>(element, title, description);
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final InsuranceSubjectAttribute warranty = (InsuranceSubjectAttribute) object;
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
