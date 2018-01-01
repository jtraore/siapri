package com.siapri.broker.app.views.sinister;

import java.time.ZonedDateTime;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class SinisterDatalistModel extends DataListModel<Sinister> {

	public SinisterDatalistModel(final Composite parent) {
		super();
	}

	@Override
	protected void initialize() {

		super.initialize();

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "number", "description" };

		columnDescriptors = new ColumnDescriptor[5];
		columnDescriptors[0] = new ColumnDescriptor("Numéro", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("Client", 0.20, 125);
		columnDescriptors[2] = new ColumnDescriptor("Contrat", 0.15, 125);
		columnDescriptors[3] = new ColumnDescriptor("Date", 0.15, 125);
		columnDescriptors[4] = new ColumnDescriptor("Description", 0.35, 125);
	}

	@Override
	protected List<Sinister> loadElements() {
		return BundleUtil.getService(DaoCacheService.class).getSinisters();
	}
	
	@Override
	protected ICustomizer<Sinister> createCustomizer(final Sinister element, final String title, final String description) {
		return new SinisterCustomizer(element, title, description);
	}

	@Override
	protected Sinister createObject() {
		final Sinister obj = super.createObject();
		obj.setOccuredDate(ZonedDateTime.now());
		obj.setAddress(new Address());
		return obj;
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object object, final int column) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Sinister sinister = (Sinister) object;
			final Contract contract = sinister.getContract();
			switch (column) {
				case 0:
					return sinister.getNumber();
				case 1:
					final Client client = contract.getClient();
					if (client instanceof Person) {
						return String.format("%s %s", ((Person) client).getFirstName(), ((Person) client).getLastName());
					}
					return ((Company) client).getName();
				case 2:
					return contract.getNumber();
				case 3:
					return Util.DATE_FORMATTER.format(sinister.getOccuredDate());
				case 4:
					return sinister.getDescription();
			}
			return null;
		}
	}

}
