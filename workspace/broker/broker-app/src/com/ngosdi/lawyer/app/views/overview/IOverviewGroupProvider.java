package com.ngosdi.lawyer.app.views.overview;

import java.util.List;

public interface IOverviewGroupProvider<T> {

	public String getTitle();

	public List<OverviewItem<T>> getOverviewItems();

	public IOverviewItemLocator<T> getItemLocator();
}
