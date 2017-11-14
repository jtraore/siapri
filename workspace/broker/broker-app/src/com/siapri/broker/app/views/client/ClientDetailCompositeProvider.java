package com.siapri.broker.app.views.client;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.contract.ContractDataListModel;
import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.business.model.Person;

public class ClientDetailCompositeProvider extends AbstractDetailCompositeProvider<Person> {

	private final Map<Person, ClientDetail> clientDetails;

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
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 25;
		composite.setLayout(layout);
		
		createGeneralComposite(composite, item);
		createContractComposite(composite, item);
		createSinisterComposite(composite, item);
		
		return composite;
	}

	private void createGeneralComposite(final Composite parent, final Person item) {
		final Composite composite = createColumnComposite(parent);
	}
	
	private void createContractComposite(final Composite parent, final Person item) {
		final Composite composite = createColumnComposite(parent);
		new TitledSeparator(composite, "Liste des contrats").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		final ContractDataListModel dataListModel = new ContractDataListModel(composite);
		dataListModel.setFilterDisplayed(false);
		dataListModel.setReportButtonDisplayed(false);
		final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		// gridData.heightHint = 150;
		dataListComposite.setLayoutData(gridData);
	}

	private void createSinisterComposite(final Composite parent, final Person item) {
		final Composite composite = createColumnComposite(parent);
	}
	
	private Composite createColumnComposite(final Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		columnComposite.setLayout(new GridLayout());
		// ((GridLayout) columnComposite.getLayout()).verticalSpacing = 5;
		return columnComposite;
	}

}
