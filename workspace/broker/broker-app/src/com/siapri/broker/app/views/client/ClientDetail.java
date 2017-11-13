package com.siapri.broker.app.views.client;

import java.util.ArrayList;
import java.util.List;

import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Person;

public class ClientDetail {
	
	private final Person client;
	private final List<Contract> contracts = new ArrayList<>();
	
	public ClientDetail(final Person client) {
		this.client = client;
	}
	
	public final Person getClient() {
		return client;
	}
	
	public List<Contract> getContracts() {
		return contracts;
	}
	
}
