package com.siapri.broker.app.views.contract;

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
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class ContractDataListModel extends DataListModel<Contract> {

	public ContractDataListModel(final Composite parent) {
		super();
	}
	
	@Override
	protected void initialize() {
		
		super.initialize();
		
		labelProvider = new DataListLabelProvider();
		
		xPathExpressions = new String[] { "number", "client.firstName", "client.LastName" };
		
		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("Number", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("Date", 0.15, 125);
		columnDescriptors[2] = new ColumnDescriptor("Client", 0.30, 125);
		columnDescriptors[3] = new ColumnDescriptor("Assurance", 0.40, 125);
	}
	
	@Override
	protected List<Contract> loadElements() {
		return BundleUtil.getService(DaoCacheService.class).getContracts();
	}

	@Override
	protected ICustomizer<Contract> createCustomizer(final Contract element, final String title, final String description) {
		return new ContractCustomizer(element, retrieveInsurers(), retrieveInsuranceTypes(), title, description);
	}

	private List<Company> retrieveInsurers() {
		return BundleUtil.getService(DaoCacheService.class).getInsurers();
	}

	private List<InsuranceType> retrieveInsuranceTypes() {
		return BundleUtil.getService(DaoCacheService.class).getInsuranceTypes();
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}
		
		@Override
		public String getColumnText(final Object object, final int column) {
			final Contract contract = (Contract) object;
			final Client client = contract.getClient();
			switch (column) {
				case 0:
					return contract.getNumber();
				case 1:
					return Util.DATE_FORMATTER.format(contract.getSubscriptionDate());
				case 2:
					if (client instanceof Person) {
						final Person person = (Person) client;
						return String.format("%s %s", person.getFirstName(), person.getLastName());
					}
					final Company company = (Company) client;
					return String.format("%s - %s", company.getSiret(), company.getName());
				case 3:
					return contract.getWarrantyFormula().getName();
			}
			return null;
		}
	}
}
