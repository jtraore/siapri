package com.siapri.broker.app.views.contract;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.customizer.DocumentList;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.IBasicDaoService;

public class ContractDataListModel extends DataListModel {

	private List<Contract> contracts;
	
	public ContractDataListModel(final Composite parent) {
		inititalize(parent);
	}
	
	private void inititalize(final Composite parent) {
		
		labelProvider = new DataListLabelProvider();
		
		xPathExpressions = new String[] { "number", "client.firstName", "client.LastName" };
		
		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("Number", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("Date", 0.15, 125);
		columnDescriptors[2] = new ColumnDescriptor("Client", 0.30, 125);
		columnDescriptors[3] = new ColumnDescriptor("Assurance", 0.40, 125);
		
		final IAction createAction = (event) -> {
			final Contract contract = new Contract();
			final String title = "Nouveau contrat";
			final String description = String.format("Cette fenêtre permet de créer un nouveau contrat");
			final ContractCustomizer customizer = new ContractCustomizer(contract, retrieveInsuranceTypes(), title, description);
			final DocumentList documentList = new DocumentList(contract.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				// Save to DB
				return BundleUtil.getService(IBasicDaoService.class).save(contract);
			}
			return null;
		};
		
		final IAction editAction = (event) -> {
			final Contract contract = (Contract) event.getTarget();
			final String title = "Edition d'un contrat";
			final String description = String.format("Cette fenêtre permet d'éditer un contrat");
			final ContractCustomizer customizer = new ContractCustomizer(contract, retrieveInsuranceTypes(), title, description);
			final DocumentList documentList = new DocumentList(contract.getDocuments());
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer);
			
			if (dialog.open() == Window.OK) {
				// Merge to DB
				return BundleUtil.getService(IBasicDaoService.class).save(contract);
			}
			// else {
			// BundleUtil.getService(IBasicDaoService.class).find(InsuranceType.class, contract.getId()).ifPresent(entity -> {
			// contract.getWarranties().clear();
			// contract.getWarranties().addAll(entity.getWarranties());
			// });
			// }
			
			return null;
		};
		
		final IAction deleteAction = (event) -> {
			final Contract contract = (Contract) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IBasicDaoService.class).delete(contract);
			return contract;
		};
		
		actionModel = new DataListActionModel(createAction, editAction, deleteAction);
		
		contracts = retrieveContracts();
		dataList = new WritableList<Object>(new ArrayList<>(contracts), InsuranceType.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};
		
	}
	
	protected List<Contract> retrieveContracts() {
		return BundleUtil.getService(IBasicDaoService.class).getAll(Contract.class);
	}

	private List<InsuranceType> retrieveInsuranceTypes() {
		return BundleUtil.getService(IBasicDaoService.class).getAll(InsuranceType.class);
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
					return Util.DATE_TIME_FORMATTER.format(contract.getSubscriptionDate());
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
