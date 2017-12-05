package com.siapri.broker.app.views.insurer;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.company.CompanyOverviewItemLocator;
import com.siapri.broker.business.model.Company;

public class InsurerOverviewItemLocator extends CompanyOverviewItemLocator {
	
	@Override
	protected String getItemName() {
		return "insurers";
	}

	@Override
	protected String getPartName() {
		return getItemName();
	}
	
	@Override
	protected Class<? extends PartView<Company>> getPartViewClass() {
		return InsurerView.class;
	}
}
