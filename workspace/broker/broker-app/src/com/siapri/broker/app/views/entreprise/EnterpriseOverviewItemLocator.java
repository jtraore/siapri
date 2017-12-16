package com.siapri.broker.app.views.entreprise;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.company.CompanyOverviewItemLocator;

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
	protected Class<? extends PartView<?>> getPartViewClass() {
		return EnterpriseView.class;
	}

}
