package com.siapri.broker.app.views.entreprise;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.company.CompanyOverviewItemLocator;
import com.siapri.broker.business.model.Company;

public class EnterpriseOverviewItemLocator extends CompanyOverviewItemLocator {

	@Override
	protected String getItemName() {
		return "enterprises";
	}

	@Override
	protected String getPartName() {
		return getItemName();
	}
	
	@Override
	protected Class<? extends PartView<Company>> getPartViewClass() {
		return EnterpriseView.class;
	}
	
}
