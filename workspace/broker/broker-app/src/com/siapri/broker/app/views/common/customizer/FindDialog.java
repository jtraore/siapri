package com.siapri.broker.app.views.common.customizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.common.datalist.DataListModel;

public class FindDialog extends DialogBox {
	
	private final List<DataListModel> dataListModels = new ArrayList<>();
	
	private final String title;
	
	private final String description;
	
	private DataListComposite composite;
	
	public FindDialog(final Shell parentShell, final DataListModel dataListModel, final String title, final String description) {
		this(parentShell, Arrays.asList(dataListModel), title, description);
	}
	
	public FindDialog(final Shell parentShell, final List<DataListModel> dataListModels, final String title, final String description) {
		super(parentShell);
		this.dataListModels.addAll(dataListModels);
		this.title = title;
		this.description = description;
	}
	
	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);
		if (dataListModels.size() == 1) {
			composite = createContent(area, dataListModels.get(0));
		} else {
			createTabControl(area);
		}
		return area;
	}
	
	private Control createTabControl(final Composite parent) {
		final Composite area = (Composite) super.createDialogArea(parent);

		final CTabFolder tabFolder = new CTabFolder(area, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		dataListModels.forEach(dataListModel -> {
			final DataListComposite content = createContent(tabFolder, dataListModel);
			content.setLayoutData(new GridData(GridData.FILL_BOTH));
			final CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
			tabItem.setText(dataListModel.getTitle());
			tabItem.setControl(content);
		});

		// composite = (DataListComposite) tabFolder.getSelection().getControl();
		
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				composite = (DataListComposite) tabFolder.getSelection().getControl();
			}
		});
		
		return area;
	}
	
	private DataListComposite createContent(final Composite parent, final DataListModel dataListModel) {
		final DataListComposite composite = new DataListComposite(parent, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 500;
		composite.setLayoutData(gridData);
		return composite;
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
