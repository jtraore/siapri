package com.siapri.broker.app.views.sinister;

import java.time.ZonedDateTime;
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
import com.siapri.broker.app.views.common.action.ContextualAction;
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
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.IBasicDaoService;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class SinisterDatalistModel extends DataListModel {
	
	private List<Sinister> sinisters;
	
	public SinisterDatalistModel(final Composite parent) {
		initialize(parent);
	}
	
	private void initialize(final Composite parent) {
		labelProvider = new DataListLabelProvider();
		
		xPathExpressions = new String[] { "number", "description" };
		
		columnDescriptors = new ColumnDescriptor[5];
		columnDescriptors[0] = new ColumnDescriptor("Numéro", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("Client", 0.20, 125);
		columnDescriptors[2] = new ColumnDescriptor("Contrat", 0.15, 125);
		columnDescriptors[3] = new ColumnDescriptor("Date", 0.15, 125);
		columnDescriptors[4] = new ColumnDescriptor("Description", 0.35, 125);
		
		final IAction createAction = (event) -> {
			final Sinister sinister = new Sinister();
			sinister.setOccuredDate(ZonedDateTime.now());
			sinister.setAddress(new Address());
			final String title = "Nouveau sinistre";
			final String description = String.format("Cette fenêtre permet de déclarer un sinistre");
			final SinisterCustomizer customizer = new SinisterCustomizer(sinister, title, description);
			final DocumentList documentList = new DocumentList(sinister.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				return BundleUtil.getService(IBasicDaoService.class).save(sinister);
			}
			return null;
		};
		
		final IAction editAction = (event) -> {
			final Sinister sinister = (Sinister) event.getTarget();
			final String title = "Edition d'un sinistre";
			final String description = String.format("Cette fenêtre permet d'éditer un sinistre");
			final SinisterCustomizer customizer = new SinisterCustomizer(sinister, title, description);
			final DocumentList documentList = new DocumentList(sinister.getDocuments());
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				return BundleUtil.getService(IBasicDaoService.class).save(sinister);
			}
			return null;
		};
		
		final IAction deleteAction = (event) -> {
			final Sinister sinister = (Sinister) event.getTarget();
			BundleUtil.getService(IBasicDaoService.class).delete(sinister);
			return sinister;
		};
		
		actionModel = new DataListActionModel(createAction, editAction, deleteAction, createDatalistMenuActions(parent));
		sinisters = retrieveSinisters();
		dataList = new WritableList<Object>(new ArrayList<>(sinisters), Sinister.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};
	}

	protected ContextualAction[] createDatalistMenuActions(final Composite parent) {
		return new ContextualAction[0];
	}
	
	protected List<Sinister> retrieveSinisters() {
		return BundleUtil.getService(DaoCacheService.class).getSinisters();
	}
	
	public List<Sinister> getSinisters() {
		return sinisters;
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
