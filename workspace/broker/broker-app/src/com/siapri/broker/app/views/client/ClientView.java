package com.siapri.broker.app.views.client;

import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Person;

public class ClientView extends PartView<Person> {

	@Override
	protected void createGui(final Composite parent) {
		
		parent.setLayout(new FillLayout());

		dataListModel = new ClientDataListModel(parent);

		final Map<Person, ClientDetail> clientDetails = ((ClientDataListModel) dataListModel).getClients().stream().map(client -> new ClientDetail(client)).collect(Collectors.toMap(ClientDetail::getClient, clientDetail -> clientDetail));
		
		partViewService.addDetailCompositeProvider(new ClientDetailCompositeProvider(currentPart.getElementId(), clientDetails));
	}

}