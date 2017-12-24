package com.siapri.broker.app.views.broker;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Broker;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.IBasicDaoService;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class BrokerDataListModel extends DataListModel {

	private List<Broker> brokers;

	public BrokerDataListModel(final Composite parent) {
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "firstName", "lastName", "login" };

		columnDescriptors = new ColumnDescriptor[5];
		columnDescriptors[0] = new ColumnDescriptor("Numéro", 0.20, 125);
		columnDescriptors[1] = new ColumnDescriptor("Login", 0.20, 125);
		columnDescriptors[2] = new ColumnDescriptor("Nom", 0.25, 125);
		columnDescriptors[3] = new ColumnDescriptor("Prénom", 0.25, 125);
		columnDescriptors[4] = new ColumnDescriptor("Téléphone", 0.10, 125);

		final IAction createAction = (event) -> {
			final Broker broker = new Broker();
			final String title = "Nouvel agent";
			final String description = String.format("Cette fenêtre permet de créer un nouvel agent");
			final BrokerCustomizer customizer = new BrokerCustomizer(broker, title, description);
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				return BundleUtil.getService(IBasicDaoService.class).save(broker);
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Broker broker = (Broker) event.getTarget();
			final String title = "Edition d'un agent";
			final String description = String.format("Cette fenêtre permet d'éditer un agent");
			final BrokerCustomizer customizer = new BrokerCustomizer(broker, title, description);
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				return BundleUtil.getService(IBasicDaoService.class).save(broker);
			}
			return null;
		};

		final IAction deleteAction = (event) -> {
			final Broker broker = (Broker) event.getTarget();
			BundleUtil.getService(IBasicDaoService.class).delete(broker);
			return broker;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction);

		brokers = retrieveBrokers();

		dataList = new WritableList<Object>(new ArrayList<>(brokers), Person.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};

	}

	private List<Broker> retrieveBrokers() {
		return BundleUtil.getService(DaoCacheService.class).getBrokers();
	}

	public List<Broker> getClients() {
		return brokers;
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
