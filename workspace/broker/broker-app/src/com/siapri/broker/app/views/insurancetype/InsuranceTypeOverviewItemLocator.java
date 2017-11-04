package com.siapri.broker.app.views.insurancetype;

import com.siapri.broker.app.Activator;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.common.datalist.DataListItemLocator;
import com.siapri.broker.business.model.InsuranceType;

public class InsuranceTypeOverviewItemLocator extends DataListItemLocator<InsuranceType> {
	
	@Override
	protected Class<? extends PartView<InsuranceType>> getPartViewClass() {
		return InsuranceTypeView.class;
	}
	
	@Override
	protected String getToolItemId() {
		return Activator.MAIN_TOOLBAR_ID + ".item.insuranceTypes";
	}
}
