package com.siapri.broker.app.views.sinister;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.action.DataListActionEvent;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.customizer.DocumentList;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.IBasicDaoService;

public class SinisterDatalistModel extends DataListModel {

	private List<Sinister> sinisters;

	public SinisterDatalistModel(final Composite parent) {
		initialize(parent);
	}

	private void initialize(final Composite parent) {
		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "description" };

		columnDescriptors = new ColumnDescriptor[4];
		columnDescriptors[0] = new ColumnDescriptor("Client", 0.2, 125);
		columnDescriptors[1] = new ColumnDescriptor("Contrat", 0.1, 125);
		columnDescriptors[2] = new ColumnDescriptor("Date", 0.1, 125);
		columnDescriptors[3] = new ColumnDescriptor("Description", 0.6, 125);

		final IAction createAction = (event) -> {
			final Sinister sinister = new Sinister();
			sinister.setAddress(new Address());
			final String title = "Nouveau sinistre";
			final String description = String.format("Cette fenêtre permet de déclarer un sinistre");
			final SinisterCustomizer customizer = new SinisterCustomizer(sinister, title, description);
			final DocumentList documentList = new DocumentList(sinister.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				// Save to DB
				final Contract contract = sinister.getContract();
				contract.getSinisters().add(sinister);
				final Contract savedContract = BundleUtil.getService(IBasicDaoService.class).save(contract);
				final Sinister savedSinister = savedContract.getSinisters().get(savedContract.getSinisters().size() - 1); //BundleUtil.getService(IBasicDaoService.class).save(sinister);
				savedSinister.setContract(contract);
				//				((DataListActionEvent) event).getDataListModel().getDataList().add(savedSinister);
				return savedSinister;
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
				// Merge
				final Contract contract = sinister.getContract();
				final Contract savedContract = BundleUtil.getService(IBasicDaoService.class).save(contract);
				final List<Sinister> contractSinisters = savedContract.getSinisters();
				final Sinister savedSinister = contractSinisters.get(contractSinisters.indexOf(sinister));
				savedSinister.setContract(contract);

				return savedSinister;
			}
			return null;
		};

		final IAction deleteAction = (event) -> {
			final Sinister sinister = (Sinister) event.getTarget();
			BundleUtil.getService(IBasicDaoService.class).delete(sinister);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(sinister);
			return sinister;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction);
		sinisters = retrieveSinisters();
		dataList = new WritableList<Object>(new ArrayList<>(sinisters), Sinister.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};
	}

	private List<Sinister> retrieveSinisters() {
		//		final List<Sinister> sinister = BundleUtil.getService(IBasicDaoService.class).getAll(Sinister.class);
		//		sinister.forEach(s -> s.setContract(getContractFromSinister(s)));
		//		return sinister;
		final List<Sinister> sinisters = new ArrayList<>();
		final List<Contract> contracts = BundleUtil.getService(IBasicDaoService.class).getAll(Contract.class);
		contracts.forEach(c -> {
			sinisters.addAll(c.getSinisters());
			c.getSinisters().forEach(s -> s.setContract(c));
		});
		return sinisters;
	}

	public List<Sinister> getSinisters() {
		return sinisters;
	}

	//	private static Contract getContractFromSinister(final Sinister sinister) {
	//		final List<Contract> contracts = BundleUtil.getService(IBasicDaoService.class).getAll(Contract.class);
	//		return contracts.stream().filter(contract -> contract.getSinisters().contains(sinister)).findFirst().get();
	//	}

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
				return contract.getClient().getId().toString();
			case 1:
				return contract.getNumber();
			case 2:
				return sinister.getOccuredDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
			case 3:
				return sinister.getDescription();
			}
			return null;
		}
	}

}
