package com.siapri.broker.app.views.client;

import java.util.ArrayList;
import java.util.List;

import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Sinister;

public class ClientDetail {

	private final Client client;
	private final List<Contract> contracts = new ArrayList<>();
	private final List<Sinister> sinisters = new ArrayList<>();

	public ClientDetail(final Client client) {
		this.client = client;
	}

	public final Client getClient() {
		return client;
	}

	public final List<Contract> getContracts() {
		return contracts;
	}

	public List<Sinister> getSinisters() {
		return sinisters;
	}

}
