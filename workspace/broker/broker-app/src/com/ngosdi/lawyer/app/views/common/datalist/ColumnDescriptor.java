package com.ngosdi.lawyer.app.views.common.datalist;

import org.eclipse.jface.viewers.CellLabelProvider;

public class ColumnDescriptor {

	private final String columnName;
	private final double columnWeight;
	private final int columnInitialWidth;
	private final CellLabelProvider cellLabelProvider;
	
	public ColumnDescriptor(String columnName, double columnWeigth, int columnInitialWidth) {
		this(columnName, columnWeigth, columnInitialWidth, null);
	}
	
	public ColumnDescriptor(String columnName, double columnWeight, int columnInitialWidth, CellLabelProvider cellLabelProvider) {
		this.columnName = columnName;
		this.columnWeight = columnWeight;
		this.columnInitialWidth = columnInitialWidth;
		this.cellLabelProvider = cellLabelProvider;
	}
	
	public final String getColumnName() {
		return columnName;
	}
	
	public final double getColumnWeight() {
		return columnWeight;
	}
	
	public final int getColumnInitialWidth() {
		return columnInitialWidth;
	}
	
	public final CellLabelProvider getCellLabelProvider() {
		return cellLabelProvider;
	}
	
}
