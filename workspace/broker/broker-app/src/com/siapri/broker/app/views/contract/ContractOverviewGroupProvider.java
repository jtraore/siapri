package com.siapri.broker.app.views.contract;

import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.overview.IOverviewGroupProvider;
import com.siapri.broker.app.views.overview.IOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.IBasicDaoService;

public class ContractOverviewGroupProvider implements IOverviewGroupProvider<Contract> {
	
	@Override
	public String getTitle() {
		return "Derniers contrats enregistrés";
	}
	
	@Override
	public List<OverviewItem<Contract>> getOverviewItems() {
		return getOverviewClients().stream().map(client -> createOverviewItem(client)).collect(Collectors.toList());
	}
	
	@Override
	public IOverviewItemLocator<Contract> getItemLocator() {
		return new ContractOverviewItemLocator();
	}
	
	public static OverviewItem<Contract> createOverviewItem(final Contract contract) {
		final Client client = contract.getClient();
		if (client instanceof Person) {
			final Person person = (Person) client;
			return new OverviewItem<>(contract, String.format("%s - %s %s", contract.getNumber(), person.getFirstName(), person.getLastName()));
		}
		return new OverviewItem<>(contract, String.format("%s - %s", contract.getNumber(), ((Company) client).getName()));
	}
	
	protected List<Contract> getOverviewClients() {
		return BundleUtil.getService(IBasicDaoService.class).getLatestElements(Contract.class, 10);
	}
}
