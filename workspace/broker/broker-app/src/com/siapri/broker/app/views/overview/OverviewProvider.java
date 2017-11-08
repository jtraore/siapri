package com.siapri.broker.app.views.overview;

import java.util.ArrayList;
import java.util.List;

import com.siapri.broker.app.views.client.ClientOverviewGroupProvider;
import com.siapri.broker.app.views.contract.ContractOverviewGroupProvider;

public final class OverviewProvider {
	
	public static final OverviewProvider INSTANCE = new OverviewProvider();
	
	private final List<IOverviewGroupProvider<?>> overviewGroupProviders = new ArrayList<>();
	
	private OverviewProvider() {
		overviewGroupProviders.add(new ClientOverviewGroupProvider());
		overviewGroupProviders.add(new ContractOverviewGroupProvider());
	}
	
	public List<IOverviewGroupProvider<?>> getOverviewGroupProviders() {
		return overviewGroupProviders;
	}
}
