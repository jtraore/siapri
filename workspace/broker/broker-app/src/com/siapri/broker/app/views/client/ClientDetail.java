package com.siapri.broker.app.views.client;

import com.siapri.broker.business.model.Client;

public class ClientDetail {
	
	private final Client client;
	
	public ClientDetail(final Client client) {
		this.client = client;
	}
	
	public final Client getClient() {
		return client;
	}
	
}
