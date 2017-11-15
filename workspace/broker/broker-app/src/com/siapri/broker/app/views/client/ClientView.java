package com.siapri.broker.app.views.client;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.IBasicDaoService;

public class ClientView extends PartView<Person> {
	
	private Map<Client, List<Contract>> contractsByClient;
	private Map<Person, ClientDetail> clientDetails;

	@Override
	protected void createGui(final Composite parent) {

		parent.setLayout(new FillLayout());
		
		dataListModel = new ClientDataListModel(parent);

		contractsByClient = getContractsByClient();
		
		clientDetails = ((ClientDataListModel) dataListModel).getClients().stream().map(this::createClientDetail).collect(Collectors.toMap(ClientDetail::getClient, Function.identity()));

		partViewService.addDetailCompositeProvider(new ClientDetailCompositeProvider(currentPart.getElementId(), clientDetails));
	}

	@Inject
	@Optional
	private void itemCreate(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item instanceof Contract) {
			Contract contract = (Contract) item;
			clientDetails.get(contract.getClient()).getContracts().add(contract);
		} else if (item instanceof Person) {
			Person client = (Person) item;
			clientDetails.put(client, createClientDetail(client));
		}
	}

	@Inject
	@Optional
	private void itemEdited(@UIEventTopic(IApplicationEvent.ITEM_EDITED) final Object item) {

	}

	@Inject
	@Optional
	private void itemRemoved(@UIEventTopic(IApplicationEvent.ITEM_REMOVED) final Object item) {

	}

	private ClientDetail createClientDetail(final Person client) {
		final ClientDetail clientDetail = new ClientDetail(client);
		clientDetail.getContracts().addAll(contractsByClient.get(client));
		return clientDetail;
	}

	private Map<Client, List<Contract>> getContractsByClient() {
		return BundleUtil.getService(IBasicDaoService.class).getAll(Contract.class).stream().collect(Collectors.groupingBy(Contract::getClient));
	}
	
}