package com.siapri.broker.app.views.contract;

import com.siapri.broker.app.Activator;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.common.datalist.DataListItemLocator;
import com.siapri.broker.business.model.Contract;

public class ContractOverviewItemLocator extends DataListItemLocator<Contract> {
	
	@Override
	protected Class<? extends PartView<Contract>> getPartViewClass() {
		return ContractView.class;
	}
	
	@Override
	protected String getToolItemId() {
		return Activator.MAIN_TOOLBAR_ID + ".item.contracts";
	}
}
