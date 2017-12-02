package com.siapri.broker.app.views.company;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Company;

public class CompanyView extends PartView<Company> {
	
	@Override
	protected void createGui(final Composite parent) {
		
		parent.setLayout(new FillLayout());
		
		dataListModel = new CompanyDataListModel(parent);
	}
	
}
