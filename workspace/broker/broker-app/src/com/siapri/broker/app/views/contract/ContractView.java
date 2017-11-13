package com.siapri.broker.app.views.contract;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Contract;

public class ContractView extends PartView<Contract> {
	
	@Override
	protected void createGui(final Composite parent) {

		parent.setLayout(new FillLayout());
		
		dataListModel = new ContractDataListModel(parent);
	}
	
}