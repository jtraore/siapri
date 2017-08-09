package com.siapri.broker.app.views.common.action;

import com.siapri.broker.app.views.common.datalist.DataListModel;

public class DataListActionEvent extends ActionEvent {

	private final DataListModel dataListModel;

	public DataListActionEvent(final Object source, final DataListModel dataListModel) {
		super(source);
		this.dataListModel = dataListModel;
	}

	public DataListActionEvent(final Object source, final Object target, final DataListModel dataListModel) {
		super(source, target);
		this.dataListModel = dataListModel;
	}

	public DataListModel getDataListModel() {
		return dataListModel;
	}
}
