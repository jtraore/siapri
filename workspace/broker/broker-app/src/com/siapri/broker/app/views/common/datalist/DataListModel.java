package com.siapri.broker.app.views.common.datalist;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;

public class DataListModel {
	
	protected WritableList<Object> dataList;
	protected ITableLabelProvider labelProvider;
	protected ColumnDescriptor[] columnDescriptors;
	protected DataListActionModel actionModel;
	protected String[] xPathExpressions;
	
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
	
	/**
	 * @return the xPathExpressions
	 */
	public String[] getxPathExpressions() {
		return xPathExpressions;
	}
	
	/**
	 * @param xPathExpressions
	 *            the xPathExpressions to set
	 */
	public void setxPathExpressions(final String[] xPathExpressions) {
		this.xPathExpressions = xPathExpressions;
	}
	
	/**
	 * @param dataList
	 *            the dataList to set
	 */
	public void setDataList(final WritableList<Object> dataList) {
		this.dataList = dataList;
	}
	
	/**
	 * @param labelProvider
	 *            the labelProvider to set
	 */
	public void setLabelProvider(final ITableLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}
	
	/**
	 * @param columnDescriptors
	 *            the columnDescriptors to set
	 */
	public void setColumnDescriptors(final ColumnDescriptor[] columnDescriptors) {
		this.columnDescriptors = columnDescriptors;
	}
	
	/**
	 * @param actionModel
	 *            the actionModel to set
	 */
	public void setActionModel(final DataListActionModel actionModel) {
		this.actionModel = actionModel;
	}
	
	public Object getElementType() {
		return dataList.getElementType();
	}
}
