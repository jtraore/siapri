package com.siapri.broker.app.views.client;

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
import com.siapri.broker.app.views.common.action.DataListActionEvent;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.customizer.DocumentList;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.IBasicDaoService;

public class ClientDataListModel extends DataListModel {

	private List<Person> clients;

	public ClientDataListModel(final Composite parent) {
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "firstName", "lastName", "phones[@name='MOBILE']", "phones[@name='LAND']" };

		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("Nom", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Prénom", 0.30, 125);
		columnDescriptors[2] = new ColumnDescriptor("Téléphone", 0.10, 125);
		columnDescriptors[3] = new ColumnDescriptor("Adresse", 0.30, 125);

		final IAction createAction = (event) -> {
			final Person client = new Person();
			final String title = "Nouveau client";
			final String description = String.format("Cette fenêtre permet de créer un nouveau client");
			final ClientCustomizer customizer = new ClientCustomizer(client, title, description);
			final DocumentList documentList = new DocumentList(client.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				// Save to DB
				final Person savedClient = BundleUtil.getService(IBasicDaoService.class).save(client);
				((DataListActionEvent) event).getDataListModel().getDataList().add(savedClient);
				return savedClient;
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Person client = (Person) event.getTarget();
			final String title = "Edition d'un client";
			final String description = String.format("Cette fenêtre permet d'éditer un nouveau client");
			final ClientCustomizer customizer = new ClientCustomizer(client, title, description);
			final DocumentList documentList = new DocumentList(client.getDocuments());
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);

			if (dialog.open() == Window.OK) {
				// Merge to DB
				return BundleUtil.getService(IBasicDaoService.class).save(client);
			}
			return null;
		};

		final IAction deleteAction = (event) -> {
			final Client client = (Client) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IBasicDaoService.class).delete(client);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(client);
			return client;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction);

		clients = retrieveClients();
		dataList = new WritableList<Object>(new ArrayList<>(clients), Person.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};

	}

	private List<Person> retrieveClients() {
		return BundleUtil.getService(IBasicDaoService.class).getAll(Person.class);
	}

	public List<Person> getClients() {
		return clients;
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
					return client.getFirstName();
				case 1:
					return client.getLastName();
				case 2:
					return client.getPhones().get(EPhoneType.MOBILE.name());
				case 3:
					final Address homeAddress = client.getAddresses().get(EAddressType.HOME.name());
					return String.format("%s, %s, %s, %s, %s", homeAddress.getNumber(), homeAddress.getStreet(), homeAddress.getPostalCode(), homeAddress.getCity(), homeAddress.getCountry());
			}
			return null;
		}
	}
}
