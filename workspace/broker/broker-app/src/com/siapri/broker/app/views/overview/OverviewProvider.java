package com.siapri.broker.app.views.overview;

import java.util.ArrayList;
import java.util.List;

import com.siapri.broker.app.views.client.ClientOverviewGroupProvider;

public final class OverviewProvider {

	public static final OverviewProvider INSTANCE = new OverviewProvider();

	private final List<IOverviewGroupProvider<?>> overviewGroupProviders = new ArrayList<>();

	private OverviewProvider() {
		// overviewGroupProviders.add(new HearingOverviewGroupProvider());
		// overviewGroupProviders.add(new CaseOverviewGroupProvider());
		overviewGroupProviders.add(new ClientOverviewGroupProvider());
	}

	public List<IOverviewGroupProvider<?>> getOverviewGroupProviders() {
		return overviewGroupProviders;
	}
}
