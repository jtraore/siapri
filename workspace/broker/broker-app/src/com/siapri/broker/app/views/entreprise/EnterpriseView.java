package com.siapri.broker.app.views.entreprise;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.client.ClientDetailCompositeProvider;
import com.siapri.broker.app.views.client.ClientView;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.app.views.company.CompanyDataListModel;
import com.siapri.broker.business.model.Client;

public class EnterpriseView extends ClientView {

	@Override
	protected ClientDetailCompositeProvider getDetailCompositeProvider() {
		return new EntrepriseDetailCompositeProvider(currentPart.getElementId());
	}

	@Override
	protected List<? extends Client> getClients() {
		return ((CompanyDataListModel) dataListModel).getElements();
	}
	
	@Override
	protected DataListModel<?> getDataListModel(final Composite parent) {
		return new CompanyDataListModel(parent, false);
	}
}
