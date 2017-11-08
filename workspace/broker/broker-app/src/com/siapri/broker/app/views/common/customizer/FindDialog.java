package com.siapri.broker.app.views.common.customizer;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.common.datalist.DataListModel;

public class FindDialog extends DialogBox {
	
	private final DataListModel dataListModel;
	
	private final String title;
	
	private final String description;
	
	private DataListComposite composite;
	
	public FindDialog(final Shell parentShell, final DataListModel dataListModel, final String title, final String description) {
		super(parentShell);
		this.dataListModel = dataListModel;
		this.title = title;
		this.description = description;
	}
	
	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		composite = new DataListComposite(area, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 500;
		composite.setLayoutData(gridData);
		return area;
	}
	
	@Override
	public void create() {
		super.create();
		setTitle(title);
		setMessage(description, IMessageProvider.INFORMATION);
	}
	
	public Object getSelectedItem() {
		return composite.getSelectedItem();
	}
}
