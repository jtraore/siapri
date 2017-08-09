package com.siapri.broker.app.views.overview;

@FunctionalInterface
public interface IOverviewItemLocator<T> {
	public void locate(OverviewItem<T> item);
}
