package com.siapri.broker.app.views.sinister;

import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Sinister;

public class SinisterView extends PartView<Sinister> {
	
	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new FillLayout());
		dataListModel = new SinisterDatalistModel(parent);
		final Map<Sinister, SinisterDetail> sinisterDetails = ((SinisterDatalistModel) dataListModel).getSinisters().stream().map(sinister -> new SinisterDetail(sinister))
				.collect(Collectors.toMap(SinisterDetail::getSinister, sinisterDetail -> sinisterDetail));
	}

}
