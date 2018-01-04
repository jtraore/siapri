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
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class ClientView extends PartView<Person> {

	private ClientDetailCompositeProvider detailCompositeProvider;

	@Override
	protected void createGui(final Composite parent) {

		parent.setLayout(new FillLayout());

		dataListModel = getDataListModel(parent);

		detailCompositeProvider = getDetailCompositeProvider();
		refreshDetails();
		partViewService.addDetailCompositeProvider(detailCompositeProvider);
	}

	private void refreshDetails() {
		// @formatter:off
		final Map<Client, ClientDetail> clientDetails = getClients()
				.stream()
				.map(this::createClientDetail)
				.collect(Collectors.toMap(ClientDetail::getClient, Function.identity()));
		// @formatter:on
		
		detailCompositeProvider.setClientDetails(clientDetails);
	}
	
	protected ClientDetailCompositeProvider getDetailCompositeProvider() {
		return new ClientDetailCompositeProvider(currentPart.getElementId());
	}

	protected List<? extends Client> getClients() {
		return ((ClientDataListModel) dataListModel).getElements();
	}

	protected DataListModel<?> getDataListModel(final Composite parent) {
		return new ClientDataListModel(parent);
	}

	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item instanceof Contract || item instanceof Sinister || item instanceof Client) {
			refreshDetails();
		}
	}

	@Inject
	@Optional
	private void itemEdited(@UIEventTopic(IApplicationEvent.ITEM_EDITED) final Object item) {
	}

	@Inject
	@Optional
	private void itemRemoved(@UIEventTopic(IApplicationEvent.ITEM_REMOVED) final Object item) {
		if (item instanceof Contract || item instanceof Sinister || item instanceof Client) {
			refreshDetails();
		}
	}

	private ClientDetail createClientDetail(final Client client) {
		final ClientDetail clientDetail = new ClientDetail(client);
		clientDetail.getContracts().addAll(BundleUtil.getService(DaoCacheService.class).getContracts(client));
		clientDetail.getSinisters().addAll(BundleUtil.getService(DaoCacheService.class).getSinisters(client));
		return clientDetail;
	}
}