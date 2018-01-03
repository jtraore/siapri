package com.siapri.broker.app.views.entreprise;

import com.siapri.broker.app.views.client.ClientDetailCompositeProvider;
import com.siapri.broker.business.model.Company;

public class EntrepriseDetailCompositeProvider extends ClientDetailCompositeProvider {
	
	public EntrepriseDetailCompositeProvider(final String id) {
		super(id);
	}

	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Company && !((Company) item).isInsurer();
	}
}
