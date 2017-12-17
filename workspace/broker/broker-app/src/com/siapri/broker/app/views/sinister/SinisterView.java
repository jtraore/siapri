package com.siapri.broker.app.views.sinister;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.model.WarrantyFormula;
import com.siapri.broker.business.service.IBasicDaoService;

public class SinisterView extends PartView<Sinister> {

	private Map<WarrantyFormula, InsuranceType> formulaMap;
	
	@Override
	protected void createGui(final Composite parent) {
		parent.setLayout(new FillLayout());
		dataListModel = new SinisterDatalistModel(parent);
		// final Map<Sinister, SinisterDetail> sinisterDetails = ((SinisterDatalistModel) dataListModel).getSinisters().stream().map(sinister -> new SinisterDetail(sinister))
		// .collect(Collectors.toMap(SinisterDetail::getSinister, sinisterDetail -> sinisterDetail));
		
		formulaMap = getWarrantyFormulas();

		partViewService.addDetailCompositeProvider(new SinisterDetailCompositeProvider(currentPart.getElementId(), formulaMap));
	}

	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item instanceof Sinister) {
			final Sinister sinister = (Sinister) item;
			dataListModel.setSelectionEventActivated(false);
			if (!dataListModel.getDataList().contains(sinister)) {
				dataListModel.getDataList().add(sinister);
			}
			dataListModel.setSelectionEventActivated(true);
		}
	}

	@Inject
	@Optional
	private void itemEdited(@UIEventTopic(IApplicationEvent.ITEM_EDITED) final Object item) {
		if (item instanceof Sinister) {
			final Sinister sinister = (Sinister) item;
			final List<Object> sinisters = dataListModel.getDataList();
			dataListModel.setSelectionEventActivated(false);
			Collections.replaceAll(sinisters, sinisters.get(sinisters.indexOf(sinister)), sinister);
			dataListModel.setSelectionEventActivated(true);
		}
	}

	@Inject
	@Optional
	private void itemRemoved(@UIEventTopic(IApplicationEvent.ITEM_REMOVED) final Object item) {
		if (item instanceof Sinister) {
			final Sinister sinister = (Sinister) item;
			dataListModel.setSelectionEventActivated(false);
			dataListModel.getDataList().remove(sinister);
			dataListModel.setSelectionEventActivated(true);
		}
	}
	
	private Map<WarrantyFormula, InsuranceType> getWarrantyFormulas() {
		final Map<WarrantyFormula, InsuranceType> formulaMap = new HashMap<>();
		BundleUtil.getService(IBasicDaoService.class).getAll(InsuranceType.class).forEach(insuranceType -> {
			insuranceType.getFormulas().forEach(formula -> formulaMap.put(formula, insuranceType));
		});
		return formulaMap;
	}
	
}
