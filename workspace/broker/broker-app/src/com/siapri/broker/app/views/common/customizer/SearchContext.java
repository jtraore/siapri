package com.siapri.broker.app.views.common.customizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;

import com.siapri.broker.app.views.common.datalist.DataListModel;

public class SearchContext {

	private final List<DataListModel> dataListModels = new ArrayList<>();
	private final Object elementType;
	private final LabelProvider labelProvider;
	private final String title;
	private final String description;
	
	public SearchContext(final List<DataListModel> dataListModels, final Object elementType, final LabelProvider labelProvider, final String title, final String description) {
		this.dataListModels.addAll(dataListModels);
		this.elementType = elementType;
		this.labelProvider = labelProvider;
		this.title = title;
		this.description = description;
	}

	public SearchContext(final DataListModel dataListModel, final LabelProvider labelProvider, final String title, final String description) {
		this(Arrays.asList(dataListModel), dataListModel.getDataList().getElementType(), labelProvider, title, description);
	}

	public List<DataListModel> getDataListModels() {
		return dataListModels;
	}

	public Object getElementType() {
		return elementType;
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
