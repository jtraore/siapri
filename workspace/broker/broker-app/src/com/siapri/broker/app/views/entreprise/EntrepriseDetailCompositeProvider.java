package com.siapri.broker.app.views.entreprise;

import java.util.Map;

import com.siapri.broker.app.views.client.ClientDetail;
import com.siapri.broker.app.views.client.ClientDetailCompositeProvider;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;

public class EntrepriseDetailCompositeProvider extends ClientDetailCompositeProvider {

	public EntrepriseDetailCompositeProvider(final String id, final Map<Client, ClientDetail> clientDetails) {
		super(id, clientDetails);
	}
	
	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Company && !((Company) item).isInsurer();
	}
}
