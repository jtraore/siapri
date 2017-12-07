package com.siapri.broker.app.views.company;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.customizer.DocumentList;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.service.IBasicDaoService;

public class CompanyDataListModel extends DataListModel {

	private List<Company> companies;

	private final boolean isInsurer;

	public CompanyDataListModel(final Composite parent, final boolean isInsurer) {
		this.isInsurer = isInsurer;
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "siret", "name", "phones[@name='MOBILE']", "phones[@name='LAND']" };

		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("siret", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("name", 0.45, 125);
		columnDescriptors[2] = new ColumnDescriptor("Téléphone", 0.10, 125);
		columnDescriptors[3] = new ColumnDescriptor("Adresse", 0.30, 125);

		final IAction createAction = (event) -> {
			final Company company = new Company();
			company.setInsurer(isInsurer);
			final String title = "Nouvelle société";
			final String description = String.format("Cette fenêtre permet de créer une nouvelle société");
			final CompanyCustomizer customizer = new CompanyCustomizer(company, title, description);
			final DocumentList documentList = new DocumentList(company.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				// Save to DB
				return BundleUtil.getService(IBasicDaoService.class).save(company);
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Company company = (Company) event.getTarget();
			final String title = "Edition d'une société";
			final String description = String.format("Cette fenêtre permet d'éditer une société");
			final CompanyCustomizer customizer = new CompanyCustomizer(company, title, description);
			final DocumentList documentList = new DocumentList(company.getDocuments());
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);

			if (dialog.open() == Window.OK) {
				// Merge to DB
				return BundleUtil.getService(IBasicDaoService.class).save(company);
			}
			return null;
		};

		final IAction deleteAction = (event) -> {
			final Client client = (Client) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IBasicDaoService.class).delete(client);
			return client;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction);

		companies = retrieveElements();
		dataList = new WritableList<Object>(new ArrayList<>(companies), Company.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};

	}

	private List<Company> retrieveElements() {
		if (isInsurer) {
			return BundleUtil.getService(IBasicDaoService.class).getInsurers(-1);
		}
		return BundleUtil.getService(IBasicDaoService.class).getEntreprises(-1);
	}

	public List<Company> getCompanies() {
		return companies;
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
					return client.getSiret();
				case 1:
					return client.getName();
				case 2:
					return client.getPhones().get(EPhoneType.LAND.name());
				case 3:
					final Address address = client.getAddresses().get(EAddressType.WORK.name());
					return Util.formatAddress(address);
			}
			return null;
		}
	}
}
