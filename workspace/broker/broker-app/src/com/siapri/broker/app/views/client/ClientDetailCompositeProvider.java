package com.siapri.broker.app.views.client;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.business.model.Person;

public class ClientDetailCompositeProvider extends AbstractDetailCompositeProvider<Person> {
	
	private final Map<Person, ClientDetail> clientDetails;
	
	public ClientDetailCompositeProvider(final String id, final Map<Person, ClientDetail> clientDetails) {
		super(id);
		this.clientDetails = clientDetails;
	}
	
	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Person;
	}
	
	@Override
	public Composite createComposite(final Composite parent, final Person item) {
		return null;
	}
	
}
