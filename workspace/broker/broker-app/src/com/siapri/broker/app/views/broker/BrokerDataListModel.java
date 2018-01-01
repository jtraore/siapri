package com.siapri.broker.app.views.broker;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Broker;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class BrokerDataListModel extends DataListModel<Broker> {

	public BrokerDataListModel(final Composite parent) {
		super();
	}

	@Override
	protected void initialize() {

		super.initialize();
		
		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "firstName", "lastName", "login" };

		columnDescriptors = new ColumnDescriptor[5];
		columnDescriptors[0] = new ColumnDescriptor("Numéro", 0.20, 125);
		columnDescriptors[1] = new ColumnDescriptor("Login", 0.20, 125);
		columnDescriptors[2] = new ColumnDescriptor("Nom", 0.25, 125);
		columnDescriptors[3] = new ColumnDescriptor("Prénom", 0.25, 125);
		columnDescriptors[4] = new ColumnDescriptor("Téléphone", 0.10, 125);
	}

	@Override
	protected List<Broker> loadElements() {
		return BundleUtil.getService(DaoCacheService.class).getBrokers();
	}
	
	@Override
	protected ICustomizer<Broker> createCustomizer(final Broker element, final String title, final String description) {
		return new BrokerCustomizer(element, title, description);
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Broker broker = (Broker) object;
			switch (column) {
				case 0:
					return broker.getNumber();
				case 1:
					return broker.getLogin();
				case 2:
					return broker.getFirstName();
				case 3:
					return broker.getLastName();
				case 4:
					return broker.getPhone();
			}
			return null;
		}
	}
}
