package com.siapri.broker.app.views.client;

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
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class ClientDataListModel extends DataListModel<Person> {

	public ClientDataListModel(final Composite parent) {
		super();
	}

	@Override
	protected void initialize() {
		
		super.initialize();

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "number", "firstName", "lastName", "phones[@name='MOBILE']", "phones[@name='LAND']" };

		columnDescriptors = new ColumnDescriptor[5];
		columnDescriptors[0] = new ColumnDescriptor("Numéro", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("Nom", 0.25, 125);
		columnDescriptors[2] = new ColumnDescriptor("Prénom", 0.25, 125);
		columnDescriptors[3] = new ColumnDescriptor("Téléphone", 0.10, 125);
		columnDescriptors[4] = new ColumnDescriptor("Adresse", 0.25, 125);
	}

	@Override
	protected List<Person> loadElements() {
		return BundleUtil.getService(DaoCacheService.class).getPersons();
	}
	
	@Override
	protected ICustomizer<Person> createCustomizer(final Person element, final String title, final String description) {
		return new ClientCustomizer(element, title, description);
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Person client = (Person) object;
			switch (column) {
				case 0:
					return client.getNumber();
				case 1:
					return client.getFirstName();
				case 2:
					return client.getLastName();
				case 3:
					return client.getPhones().get(EPhoneType.MOBILE.name());
				case 4:
					final Address homeAddress = client.getAddresses().get(EAddressType.HOME.name());
					return Util.formatAddress(homeAddress);
			}
			return null;
		}
	}
}
