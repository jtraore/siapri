package com.ngosdi.lawyer.app.views.overview;

@FunctionalInterface
public interface IOverviewItemLocator<T> {
	public void locate(OverviewItem<T> item);
}
