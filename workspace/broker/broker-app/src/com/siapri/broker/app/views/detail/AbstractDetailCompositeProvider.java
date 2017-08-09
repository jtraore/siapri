package com.siapri.broker.app.views.detail;

public abstract class AbstractDetailCompositeProvider<T> implements IDetailCompositeProvider<T> {

	private String id;

	public AbstractDetailCompositeProvider(final String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
