package com.siapri.broker.app.views.client;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;

public class ClientDetailCompositeProvider extends AbstractDetailCompositeProvider<Client> {

	private final Map<Client, ClientDetail> clientDetails;

	private Client currentClient;

	public ClientDetailCompositeProvider(final String id, final Map<Client, ClientDetail> clientDetails) {
		super(id);
		this.clientDetails = clientDetails;
	}

	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Person;
	}

	@Override
	public Composite createComposite(final Composite parent, final Client item) {

		currentClient = item;

		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, true);
		layout.horizontalSpacing = 25;
		composite.setLayout(layout);
		
		if (item instanceof Person) {
			createGeneralComposite(composite, (Person) item);
		} else {
			createGeneralComposite(composite, (Company) item);
		}
		createContractComposite(composite, item);
		createSinisterComposite(composite, item);
		
		return composite;
	}

	private void createGeneralComposite(final Composite parent, final Person item) {
		createPersonComposite(parent, item, false);
	}
	
	private void createGeneralComposite(final Composite parent, final Company item) {
		createEntrepriseComposite(parent, item, false);
	}
	
	private void createContractComposite(final Composite parent, final Client item) {
		createContractListComposite(parent, new ContractDataListLabelProvider());
	}

	private void createSinisterComposite(final Composite parent, final Client item) {
		createSinisterListComposite(parent, new SinisterDataListLabelProvider());
	}
	
	@Override
	protected List<Contract> getContextContracts() {
		return clientDetails.get(currentClient).getContracts();
	}
	
	@Override
	protected List<Sinister> getContextSinisters() {
		return clientDetails.get(currentClient).getSinisters();
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
