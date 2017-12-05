package com.siapri.broker.app.views.company;

import com.siapri.broker.app.Activator;
import com.siapri.broker.app.views.common.datalist.DataListItemLocator;
import com.siapri.broker.business.model.Company;

public abstract class CompanyOverviewItemLocator extends DataListItemLocator<Company> {
	
	@Override
	protected String getToolItemId() {
		return Activator.MAIN_TOOLBAR_ID + ".item." + getItemName();
	}

	protected abstract String getItemName();
}
