package com.siapri.broker.app.views.contract;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Contract;

public class ContractView extends PartView<Contract> {

	@Override
	protected void createGui(final Composite parent) {
		
		parent.setLayout(new FillLayout());

		dataListModel = new ContractDataListModel(parent);
	}

	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item instanceof Contract) {
			final Contract contract = (Contract) item;
			dataListModel.setSelectionEventActivated(false);
			dataListModel.getDataList().add(contract);
			dataListModel.setSelectionEventActivated(true);
		}
	}
	
	@Inject
	@Optional
	private void itemEdited(@UIEventTopic(IApplicationEvent.ITEM_EDITED) final Object item) {
		if (item instanceof Contract) {
			final Contract contract = (Contract) item;
			final List<Object> contracts = dataListModel.getDataList();
			dataListModel.setSelectionEventActivated(false);
			Collections.replaceAll(contracts, contracts.get(contracts.indexOf(contract)), contract);
			dataListModel.setSelectionEventActivated(true);
		}
	}
	
	@Inject
	@Optional
	private void itemRemoved(@UIEventTopic(IApplicationEvent.ITEM_REMOVED) final Object item) {
		if (item instanceof Contract) {
			final Contract contract = (Contract) item;
			dataListModel.setSelectionEventActivated(false);
			dataListModel.getDataList().remove(contract);
			dataListModel.setSelectionEventActivated(true);
		}
	}
}