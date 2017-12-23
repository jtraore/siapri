package com.siapri.broker.app.views.contract;

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
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.model.WarrantyFormula;

public class ContractDetailCompositeProvider extends AbstractDetailCompositeProvider<Contract> {
	
	private final Map<Contract, ContractDetail> contractDetails;

	private final Map<WarrantyFormula, InsuranceType> warrantyFormulas;
	
	private Contract currentContract;

	public ContractDetailCompositeProvider(final String id, final Map<Contract, ContractDetail> contractDetails, final Map<WarrantyFormula, InsuranceType> warrantyFormulas) {
		super(id);
		this.contractDetails = contractDetails;
		this.warrantyFormulas = warrantyFormulas;
	}

	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Contract;
	}

	@Override
	public Composite createComposite(final Composite parent, final Contract item) {
		
		currentContract = item;
		
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, true);
		layout.horizontalSpacing = 25;
		composite.setLayout(layout);

		createGeneralComposite(composite, item);
		createClientComposite(composite, item);
		createSinisterListComposite(composite, item);

		return composite;
	}
	
	private void createGeneralComposite(final Composite parent, final Contract item) {
		createContractComposite(parent, item, warrantyFormulas, false);
	}

	private void createClientComposite(final Composite parent, final Contract item) {
		final Client client = item.getClient();
		if (client instanceof Person) {
			createPersonComposite(parent, (Person) client);
		} else {
			createEntrpriseComposite(parent, (Company) client);
		}
	}
	
	private void createPersonComposite(final Composite parent, final Person person) {
		createPersonComposite(parent, person, true);
	}

	private void createEntrpriseComposite(final Composite parent, final Company company) {
		createCompanyComposite(parent, company, true);
	}
	
	private void createSinisterListComposite(final Composite composite, final Contract item) {
		createSinisterListComposite(composite, new SinisterDataListLabelProvider());
	}
	
	@Override
	protected List<Sinister> getContextSinisters() {
		return contractDetails.get(currentContract).getSinisters();
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
					return Util.DATE_FORMATTER.format(sinister.getOccuredDate());
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
