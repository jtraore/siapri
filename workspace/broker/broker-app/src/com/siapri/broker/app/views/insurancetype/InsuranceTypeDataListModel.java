package com.siapri.broker.app.views.insurancetype;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.service.IBasicDaoService;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class InsuranceTypeDataListModel extends DataListModel<InsuranceType> {
	
	public InsuranceTypeDataListModel(final Composite parent) {
		super();
	}
	
	@Override
	protected void initialize() {

		super.initialize();
		
		labelProvider = new DataListLabelProvider();
		
		xPathExpressions = new String[] { "code", "name" };
		
		columnDescriptors = new ColumnDescriptor[2];
		columnDescriptors[0] = new ColumnDescriptor("code", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Name", 0.70, 125);
	}
	
	@Override
	protected List<InsuranceType> loadElements() {
		return BundleUtil.getService(DaoCacheService.class).getInsuranceTypes();
	}
	
	@Override
	protected void cancelEdit(final InsuranceType element) {
		BundleUtil.getService(IBasicDaoService.class).find(InsuranceType.class, element.getId()).ifPresent(entity -> {
			element.getWarranties().clear();
			element.getWarranties().addAll(entity.getWarranties());
			element.getAttributes().clear();
			element.getAttributes().addAll(entity.getAttributes());
		});
	}

	@Override
	protected ICustomizer<InsuranceType> createCustomizer(final InsuranceType element, final String title, final String description) {
		return new InsuranceTypeCustomizer(element, title, description);
	}
	
	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}
		
		@Override
		public String getColumnText(final Object object, final int column) {
			final InsuranceType insuranceType = (InsuranceType) object;
			switch (column) {
				case 0:
					return insuranceType.getCode();
				case 1:
					return insuranceType.getName();
			}
			return null;
		}
	}
}
