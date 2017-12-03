package com.siapri.broker.app.views.common.datalist;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;

public class DataListModel {

	protected String title;
	protected WritableList<Object> dataList;
	protected ITableLabelProvider labelProvider;
	protected ColumnDescriptor[] columnDescriptors;
	protected DataListActionModel actionModel;
	protected String[] xPathExpressions;
	protected boolean filterDisplayed = true;
	protected boolean reportButtonDisplayed = true;
	protected boolean selectionEventActivated = true;

	public DataListModel() {
	}

	public DataListModel(final WritableList<Object> dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel) {
		this(dataList, labelProvider, columnDescriptors, actionModel, null);
	}

	public DataListModel(final WritableList<Object> dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel, final String[] xPathExpressions) {
		this.dataList = dataList;
		this.labelProvider = labelProvider;
		this.columnDescriptors = columnDescriptors;
		this.actionModel = actionModel;
		this.xPathExpressions = xPathExpressions;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public WritableList<Object> getDataList() {
		return dataList;
	}

	public ITableLabelProvider getLabelProvider() {
		return labelProvider;
	}

	public final ColumnDescriptor[] getColumnDescriptors() {
		return columnDescriptors;
	}

	public final DataListActionModel getActionModel() {
		return actionModel;
	}

	public String[] getXPathExpressions() {
		return xPathExpressions;
	}

	public String[] getxPathExpressions() {
		return xPathExpressions;
	}

	public void setxPathExpressions(final String[] xPathExpressions) {
		this.xPathExpressions = xPathExpressions;
	}

	public void setDataList(final WritableList<Object> dataList) {
		this.dataList = dataList;
	}

	public void setLabelProvider(final ITableLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public void setColumnDescriptors(final ColumnDescriptor[] columnDescriptors) {
		this.columnDescriptors = columnDescriptors;
	}

	public void setActionModel(final DataListActionModel actionModel) {
		this.actionModel = actionModel;
	}

	public Object getElementType() {
		return dataList.getElementType();
	}

	public boolean isFilterDisplayed() {
		return filterDisplayed;
	}

	public void setFilterDisplayed(final boolean filterDisplayed) {
		this.filterDisplayed = filterDisplayed;
	}

	public boolean isReportButtonDisplayed() {
		return reportButtonDisplayed;
	}

	public void setReportButtonDisplayed(final boolean reportButtonDisplayed) {
		this.reportButtonDisplayed = reportButtonDisplayed;
	}
	
	public boolean isSelectionEventActivated() {
		return selectionEventActivated;
	}
	
	public void setSelectionEventActivated(final boolean selectionEventActivated) {
		this.selectionEventActivated = selectionEventActivated;
	}
}
