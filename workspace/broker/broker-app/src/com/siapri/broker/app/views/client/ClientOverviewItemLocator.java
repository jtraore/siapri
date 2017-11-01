package com.siapri.broker.app.views.client;

import com.siapri.broker.app.Activator;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.common.datalist.DataListItemLocator;
import com.siapri.broker.business.model.Person;

public class ClientOverviewItemLocator extends DataListItemLocator<Person> {

	@Override
	protected Class<? extends PartView<Person>> getPartViewClass() {
		return ClientView.class;
	}

	@Override
	protected String getToolItemId() {
		return Activator.MAIN_TOOLBAR_ID + ".item.clients";
	}
}
