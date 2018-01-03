package com.siapri.broker.app.views.sinister;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.model.WarrantyFormula;

public class SinisterDetailCompositeProvider extends AbstractDetailCompositeProvider<Sinister> {
	
	private Map<WarrantyFormula, InsuranceType> warrantyFormulas;

	public SinisterDetailCompositeProvider(final String id) {
		super(id);
	}

	public void setWarrantyFormulas(final Map<WarrantyFormula, InsuranceType> warrantyFormulas) {
		this.warrantyFormulas = warrantyFormulas;
	}
	
	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Sinister;
	}
	
	@Override
	public Composite createComposite(final Composite parent, final Sinister item) {
		
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, true);
		layout.horizontalSpacing = 25;
		composite.setLayout(layout);

		createSinisterComposite(composite, item);
		createContractComposite(composite, item.getContract());

		final Client client = item.getContract().getClient();
		if (client instanceof Person) {
			createPersonComposite(composite, (Person) client);
		} else {
			createEnterpriseComposite(composite, (Company) client);
		}

		return composite;
	}
	
	private void createPersonComposite(final Composite parent, final Person item) {
		createPersonComposite(parent, item, true);
	}

	private void createEnterpriseComposite(final Composite parent, final Company item) {
		createCompanyComposite(parent, item, true);
	}

	private void createContractComposite(final Composite parent, final Contract item) {
		createContractComposite(parent, item, warrantyFormulas, true);
		;
	}
	
	private void createSinisterComposite(final Composite parent, final Sinister item) {
		createSinisterComposite(parent, item, false);
	}
	
}
