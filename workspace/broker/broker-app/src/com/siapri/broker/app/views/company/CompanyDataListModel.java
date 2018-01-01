package com.siapri.broker.app.views.company;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class CompanyDataListModel extends DataListModel<Company> {

	private final boolean isInsurer;

	public CompanyDataListModel(final Composite parent, final boolean isInsurer) {
		super(false);
		this.isInsurer = isInsurer;
		initialize();
	}

	@Override
	protected void initialize() {
		
		super.initialize();

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "number", "siret", "name", "phones[@name='MOBILE']", "phones[@name='LAND']" };

		columnDescriptors = new ColumnDescriptor[5];
		columnDescriptors[0] = new ColumnDescriptor("Numéro", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("siret", 0.15, 125);
		columnDescriptors[2] = new ColumnDescriptor("name", 0.30, 125);
		columnDescriptors[3] = new ColumnDescriptor("Téléphone", 0.10, 125);
		columnDescriptors[4] = new ColumnDescriptor("Adresse", 0.30, 125);
	}

	@Override
	protected List<Company> loadElements() {
		if (isInsurer) {
			return BundleUtil.getService(DaoCacheService.class).getInsurers();
		}
		return BundleUtil.getService(DaoCacheService.class).getEntreprises();
	}

	@Override
	protected ICustomizer<Company> createCustomizer(final Company element, final String title, final String description) {
		return new CompanyCustomizer(element, title, description);
	}
	
	@Override
	protected Company createObject() {
		final Company obj = super.createObject();
		obj.setInsurer(isInsurer);
		return obj;
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Company client = (Company) object;
			switch (column) {
				case 0:
					return client.getNumber();
				case 1:
					return client.getSiret();
				case 2:
					return client.getName();
				case 3:
					return client.getPhones().get(EPhoneType.LAND.name());
				case 4:
					final Address address = client.getAddresses().get(EAddressType.WORK.name());
					return Util.formatAddress(address);
			}
			return null;
		}
	}
}
