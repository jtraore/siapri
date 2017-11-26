package com.siapri.broker.app.views.contract;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.WarrantyFormula;
import com.siapri.broker.business.service.IBasicDaoService;

public class ContractView extends PartView<Contract> {

	private Map<Contract, ContractDetail> contractDetails;

	private Map<WarrantyFormula, InsuranceType> formulaMap;

	@Override
	protected void createGui(final Composite parent) {
		
		parent.setLayout(new FillLayout());

		dataListModel = new ContractDataListModel(parent);
		
		// @formatter:off
		contractDetails = ((ContractDataListModel) dataListModel).getContracts()
				.stream()
				.map(c -> createContractDetail(c))
				.collect(Collectors.toMap(ContractDetail::getContract, Function.identity()));
		// @formatter:on
		
		formulaMap = getWarrantyFormulas();

		partViewService.addDetailCompositeProvider(new ContractDetailCompositeProvider(currentPart.getElementId(), contractDetails, formulaMap));
	}
	
	private ContractDetail createContractDetail(final Contract contract) {
		return new ContractDetail(contract);
	}

	private Map<WarrantyFormula, InsuranceType> getWarrantyFormulas() {
		final Map<WarrantyFormula, InsuranceType> formulaMap = new HashMap<>();
		BundleUtil.getService(IBasicDaoService.class).getAll(InsuranceType.class).forEach(insuranceType -> {
			insuranceType.getFormulas().forEach(formula -> formulaMap.put(formula, insuranceType));
		});
		return formulaMap;
	}

	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item instanceof Contract) {
			final Contract contract = (Contract) item;
			dataListModel.setSelectionEventActivated(false);
			if (!dataListModel.getDataList().contains(contract)) {
				dataListModel.getDataList().add(contract);
			}
			dataListModel.setSelectionEventActivated(true);
		} else if (item instanceof InsuranceType) {
			final InsuranceType insuranceType = (InsuranceType) item;
			insuranceType.getFormulas().forEach(formula -> formulaMap.put(formula, insuranceType));
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
		} else if (item instanceof InsuranceType) {
			final InsuranceType insuranceType = (InsuranceType) item;
			insuranceType.getFormulas().forEach(formula -> formulaMap.put(formula, insuranceType));
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
		} else if (item instanceof InsuranceType) {
			final InsuranceType insuranceType = (InsuranceType) item;
			insuranceType.getFormulas().forEach(formula -> formulaMap.remove(formula));
		}
	}
}