package com.siapri.broker.app.views.client;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.action.ContextualAction;
import com.siapri.broker.app.views.common.action.ContextualActionPathElement;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.contract.ContractDataListModel;
import com.siapri.broker.app.views.contract.ContractOverviewItemLocator;
import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.app.views.sinister.SinisterDatalistModel;
import com.siapri.broker.app.views.sinister.SinisterOverviewItemLocator;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;

public class ClientDetailCompositeProvider extends AbstractDetailCompositeProvider<Person> {

	private final Map<Person, ClientDetail> clientDetails;

	private Person currentClient;

	public ClientDetailCompositeProvider(final String id, final Map<Person, ClientDetail> clientDetails) {
		super(id);
		this.clientDetails = clientDetails;
	}

	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Person;
	}

	@Override
	public Composite createComposite(final Composite parent, final Person item) {

		currentClient = item;

		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, true);
		layout.horizontalSpacing = 25;
		composite.setLayout(layout);
		
		createGeneralComposite(composite, item);
		createContractComposite(composite, item);
		createSinisterComposite(composite, item);
		
		return composite;
	}

	private void createGeneralComposite(final Composite parent, final Person item) {
		final Composite composite = createColumnComposite(parent);
		final StyledText clientControl = new StyledText(composite, SWT.WRAP | SWT.MULTI);
		clientControl.setEditable(false);
		// @formatter:off
		clientControl.setText(String.format("%s %s %s, né(e) le %s\nAdresse domicile : %s,\nTél. : %s",
						Util.getGenderAsString(item.getGender()),
						item.getFirstName(),
						item.getLastName(),
						Util.DATE_TIME_FORMATTER.format(item.getBirthdate()),
						Util.formatAddress(item.getAddresses().get(EAddressType.HOME.name())),
						item.getPhones().get(EPhoneType.MOBILE.name())));
		// @formatter:on
		clientControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), String.format("%s %s", item.getFirstName(), item.getLastName()), SWT.BOLD | SWT.ITALIC));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), "Adresse domicile", SWT.BOLD));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), "Tél.", SWT.BOLD));

	}
	
	private void createContractComposite(final Composite parent, final Person item) {
		final Composite composite = createColumnComposite(parent);
		new TitledSeparator(composite, "Liste des contrats").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		final ContractDataListModel dataListModel = new ClientContractDataListModel(composite);
		dataListModel.setFilterDisplayed(false);
		dataListModel.setReportButtonDisplayed(false);
		final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		dataListComposite.setLayoutData(gridData);
	}

	private void createSinisterComposite(final Composite parent, final Person item) {
		final Composite composite = createColumnComposite(parent);
		new TitledSeparator(composite, "Liste des sinistres").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		final SinisterDatalistModel dataListModel = new ClientSinisterDataListModel(composite);
		dataListModel.setFilterDisplayed(false);
		dataListModel.setReportButtonDisplayed(false);
		final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		dataListComposite.setLayoutData(gridData);
	}
	
	private Composite createColumnComposite(final Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		columnComposite.setLayout(new GridLayout());
		return columnComposite;
	}
	
	private class ClientContractDataListModel extends ContractDataListModel {
		
		public ClientContractDataListModel(final Composite parent) {
			super(parent);
			columnDescriptors = new ColumnDescriptor[] { new ColumnDescriptor("Number", 0.15, 125), new ColumnDescriptor("Date", 0.15, 125), new ColumnDescriptor("Assurance", 0.60, 125) };
			labelProvider = new ContractDataListLabelProvider();
			selectionEventActivated = false;
		}

		@Override
		protected List<Contract> retrieveContracts() {
			return clientDetails.get(currentClient).getContracts();
		}
		
		@Override
		protected ContextualAction[] createDatalistMenuActions(final Composite parent) {
			final IAction navigateToAction = event -> {
				new ContractOverviewItemLocator().locate(new OverviewItem<>((Contract) event.getTarget(), ""));
				return null;
			};
			
			final ContextualActionPathElement[] navigateToPath = new ContextualActionPathElement[] { new ContextualActionPathElement("Afficher dans la vue Contrats", null) };
			
			return new ContextualAction[] { new ContextualAction(navigateToAction, navigateToPath) };
		}
	}

	private class ClientSinisterDataListModel extends SinisterDatalistModel {
		
		public ClientSinisterDataListModel(final Composite parent) {
			super(parent);
			columnDescriptors = new ColumnDescriptor[] { new ColumnDescriptor("Date", 0.15, 125), new ColumnDescriptor("Contrat", 0.15, 125), new ColumnDescriptor("Adresse", 0.35, 125), new ColumnDescriptor("Description", 0.35, 125) };
			labelProvider = new SinisterDataListLabelProvider();
			selectionEventActivated = false;
		}

		@Override
		protected List<Sinister> retrieveSinisters() {
			return clientDetails.get(currentClient).getSinisters();
		}
		
		@Override
		protected ContextualAction[] createDatalistMenuActions(final Composite parent) {
			final IAction navigateToAction = event -> {
				new SinisterOverviewItemLocator().locate(new OverviewItem<>((Sinister) event.getTarget(), ""));
				return null;
			};
			
			final ContextualActionPathElement[] navigateToPath = new ContextualActionPathElement[] { new ContextualActionPathElement("Afficher dans la vue Sinistres", null) };
			
			return new ContextualAction[] { new ContextualAction(navigateToAction, navigateToPath) };
		}
	}

	private final class ContractDataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Contract contract = (Contract) object;
			switch (column) {
				case 0:
					return contract.getNumber();
				case 1:
					return Util.DATE_TIME_FORMATTER.format(contract.getSubscriptionDate());
				case 2:
					return contract.getWarrantyFormula().getName();
			}
			return null;
		}
	}

	private final class SinisterDataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Sinister sinister = (Sinister) object;
			switch (column) {
				case 0:
					return Util.DATE_TIME_FORMATTER.format(sinister.getOccuredDate());
				case 1:
					return sinister.getContract().getNumber();
				case 2:
					return Util.formatAddress(sinister.getAddress());
				case 3:
					return sinister.getDescription();
			}
			return null;
		}
	}

}
