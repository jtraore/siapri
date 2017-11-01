package com.siapri.broker.app.views.client;

import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.overview.IOverviewGroupProvider;
import com.siapri.broker.app.views.overview.IOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.IBasicDaoService;

public class ClientOverviewGroupProvider implements IOverviewGroupProvider<Person> {
	
	@Override
	public String getTitle() {
		return "Particuliers recemment enregistrés";
	}
	
	@Override
	public List<OverviewItem<Person>> getOverviewItems() {
		return getOverviewClients().stream().map(client -> createOverviewItem(client)).collect(Collectors.toList());
	}
	
	@Override
	public IOverviewItemLocator<Person> getItemLocator() {
		return new ClientOverviewItemLocator();
	}
	
	public static OverviewItem<Person> createOverviewItem(final Person client) {
		return new OverviewItem<>(client, client.getFirstName() + " " + client.getLastName());
	}
	
	protected List<Person> getOverviewClients() {
		return BundleUtil.getService(IBasicDaoService.class).getLatestElements(Person.class, 10);
	}
}
