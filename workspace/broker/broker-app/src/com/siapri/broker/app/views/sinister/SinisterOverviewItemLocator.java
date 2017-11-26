package com.siapri.broker.app.views.sinister;

import com.siapri.broker.app.Activator;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.common.datalist.DataListItemLocator;
import com.siapri.broker.business.model.Sinister;

public class SinisterOverviewItemLocator extends DataListItemLocator<Sinister> {
	
	@Override
	protected Class<? extends PartView<Sinister>> getPartViewClass() {
		return SinisterView.class;
	}
	
	@Override
	protected String getToolItemId() {
		return Activator.MAIN_TOOLBAR_ID + ".item.sinisters";
	}
}
