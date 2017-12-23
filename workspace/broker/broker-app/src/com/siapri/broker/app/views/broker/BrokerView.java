package com.siapri.broker.app.views.broker;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Broker;

public class BrokerView extends PartView<Broker> {
	
	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new FillLayout());
		dataListModel = new BrokerDataListModel(parent);
	}

}
