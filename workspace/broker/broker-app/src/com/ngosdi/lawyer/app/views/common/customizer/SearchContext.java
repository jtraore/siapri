package com.ngosdi.lawyer.app.views.common.customizer;

import org.eclipse.jface.viewers.LabelProvider;

import com.ngosdi.lawyer.app.views.common.datalist.DataListModel;

public class SearchContext {

	private final DataListModel dataListModel;
	private final LabelProvider labelProvider;
	private final String title;
	private final String description;

	public SearchContext(final DataListModel dataListModel, final LabelProvider labelProvider, final String title, final String description) {
		this.dataListModel = dataListModel;
		this.labelProvider = labelProvider;
		this.title = title;
		this.description = description;
	}

	public final DataListModel getDataListModel() {
		return dataListModel;
	}

	public final LabelProvider getLabelProvider() {
		return labelProvider;
	}

	public final String getTitle() {
		return title;
	}

	public final String getDescription() {
		return description;
	}
}
