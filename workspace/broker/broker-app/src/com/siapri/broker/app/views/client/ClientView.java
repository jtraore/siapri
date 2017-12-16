package com.siapri.broker.app.views.client;

import java.util.Collections;
import java.util.HashMap;
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
import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.IBasicDaoService;

public class ClientView extends PartView<Person> {
	
	protected Map<Client, ClientDetail> clientDetails;
	
	@Override
	protected void createGui(final Composite parent) {
		
		parent.setLayout(new FillLayout());
		
		dataListModel = getDataListModel(parent);
		
		final Map<Client, List<Contract>> contractsByClient = getContractsByClient();
		final Map<Client, List<Sinister>> sinisterByClient = getSinistersByClient();

		// @formatter:off
		clientDetails = getClients()
				.stream()
				.map(c -> createClientDetail(c, contractsByClient, sinisterByClient))
				.collect(Collectors.toMap(ClientDetail::getClient, Function.identity()));
		// @formatter:on
		partViewService.addDetailCompositeProvider(getDetailCompositeProvider());
	}

	protected AbstractDetailCompositeProvider<? extends Client> getDetailCompositeProvider() {
		return new ClientDetailCompositeProvider(currentPart.getElementId(), clientDetails);
	}
	
	protected List<? extends Client> getClients() {
		return ((ClientDataListModel) dataListModel).getClients();
	}
	
	protected DataListModel getDataListModel(final Composite parent) {
		return new ClientDataListModel(parent);
	}
	
	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item instanceof Contract) {
			final Contract contract = (Contract) item;
			clientDetails.get(contract.getClient()).getContracts().add(contract);
		} else if (item instanceof Sinister) {
			final Sinister sinister = (Sinister) item;
			clientDetails.get(sinister.getContract().getClient()).getSinisters().add(sinister);
		} else if (item instanceof Person) {
			final Person client = (Person) item;
			clientDetails.put(client, createClientDetail(client, new HashMap<>(), new HashMap<>()));
		}
	}
	
	@Inject
	@Optional
	private void itemEdited(@UIEventTopic(IApplicationEvent.ITEM_EDITED) final Object item) {
		if (item instanceof Contract) {
			final Contract contract = (Contract) item;
			final List<Contract> contracts = clientDetails.get(contract.getClient()).getContracts();
			Collections.replaceAll(contracts, contracts.get(contracts.indexOf(contract)), contract);
		} else if (item instanceof Sinister) {
			final Sinister sinister = (Sinister) item;
			final List<Sinister> sinisters = clientDetails.get(sinister.getContract().getClient()).getSinisters();
			Collections.replaceAll(sinisters, sinisters.get(sinisters.indexOf(sinister)), sinister);
		}
	}
	
	@Inject
	@Optional
	private void itemRemoved(@UIEventTopic(IApplicationEvent.ITEM_REMOVED) final Object item) {
		if (item instanceof Person) {
			clientDetails.remove(item);
		} else if (item instanceof Contract) {
			final Contract contract = (Contract) item;
			clientDetails.get(contract.getClient()).getContracts().remove(contract);
		} else if (item instanceof Sinister) {
			final Sinister sinister = (Sinister) item;
			clientDetails.get(sinister.getContract().getClient()).getSinisters().remove(sinister);
		}
	}
	
	private ClientDetail createClientDetail(final Client client, final Map<Client, List<Contract>> contractsByClient, final Map<Client, List<Sinister>> sinisterByClient) {
		final ClientDetail clientDetail = new ClientDetail(client);
		final List<Contract> contracts = contractsByClient.get(client);
		if (contracts != null) {
			clientDetail.getContracts().addAll(contracts);
		}
		final List<Sinister> sinisters = sinisterByClient.get(client);
		if (sinisters != null) {
			clientDetail.getSinisters().addAll(sinisters);
		}
		return clientDetail;
	}
	
	private Map<Client, List<Contract>> getContractsByClient() {
		return BundleUtil.getService(IBasicDaoService.class).getAll(Contract.class).stream().collect(Collectors.groupingBy(Contract::getClient));
	}
	
	private Map<Client, List<Sinister>> getSinistersByClient() {
		return BundleUtil.getService(IBasicDaoService.class).getAll(Sinister.class).stream().collect(Collectors.groupingBy(s -> s.getContract().getClient()));
	}
	
}