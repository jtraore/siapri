package com.siapri.broker.app.views.sinister;

import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.overview.IOverviewGroupProvider;
import com.siapri.broker.app.views.overview.IOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class SinisterOverviewGroupProvider implements IOverviewGroupProvider<Sinister> {
	
	@Override
	public String getTitle() {
		return "Derniers sinistres enregistrés";
	}
	
	@Override
	public List<OverviewItem<Sinister>> getOverviewItems() {
		return getOverviewClients().stream().map(client -> createOverviewItem(client)).collect(Collectors.toList());
	}
	
	@Override
	public IOverviewItemLocator<Sinister> getItemLocator() {
		return new SinisterOverviewItemLocator();
	}
	
	public static OverviewItem<Sinister> createOverviewItem(final Sinister sinister) {
		final Client client = sinister.getContract().getClient();
		if (client instanceof Person) {
			final Person person = (Person) client;
			return new OverviewItem<>(sinister, String.format("%s - %s %s", sinister.getNumber(), person.getFirstName(), person.getLastName()));
		}
		return new OverviewItem<>(sinister, String.format("%s - %s", sinister.getNumber(), ((Company) client).getName()));
	}
	
	protected List<Sinister> getOverviewClients() {
		return BundleUtil.getService(DaoCacheService.class).getSinisters();
	}
}
