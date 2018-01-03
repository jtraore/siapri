package com.siapri.broker.app.views.insurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.company.CompanyDataListModel;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class InsurerView extends PartView<Company> {
	
	private InsurerDetailCompositeProvider detailsCompositeProvider;

	@Override
	protected void createGui(final Composite parent) {

		parent.setLayout(new FillLayout());

		dataListModel = new CompanyDataListModel(parent, true);

		detailsCompositeProvider = new InsurerDetailCompositeProvider(currentPart.getElementId());
		refreshDetails();
		partViewService.addDetailCompositeProvider(detailsCompositeProvider);
	}

	private void refreshDetails() {
		final Map<Company, List<Contract>> contractPerInsurer = BundleUtil.getService(DaoCacheService.class).getContracts().stream().collect(Collectors.groupingBy(Contract::getInsurer));
		final Map<Company, List<Sinister>> sinisterPerInsurer = BundleUtil.getService(DaoCacheService.class).getSinisters().stream().collect(Collectors.groupingBy(s -> s.getContract().getInsurer()));
		
		final Map<Company, InsurerDetail> insurerDetails = new HashMap<>();

		BundleUtil.getService(DaoCacheService.class).getInsurers().forEach(insurer -> {
			final InsurerDetail detail = new InsurerDetail(insurer);
			final List<Contract> contracts = contractPerInsurer.get(insurer);
			final List<Sinister> sinisters = sinisterPerInsurer.get(insurer);
			if (contracts != null) {
				detail.getContractNumberPerInsurance().putAll(contracts.stream().collect(Collectors.groupingBy(this::getInsuranceType, Collectors.summingInt(c -> 1))));
			}
			if (sinisters != null) {
				detail.getSinisterNumberPerInsurance().putAll(sinisters.stream().collect(Collectors.groupingBy(s -> getInsuranceType(s.getContract()), Collectors.summingInt(s -> 1))));
			}
			insurerDetails.put(insurer, detail);
		});
		
		detailsCompositeProvider.setInsurerDetails(insurerDetails);
	}
	
	private InsuranceType getInsuranceType(final Contract contract) {
		return BundleUtil.getService(DaoCacheService.class).getInsuranceTypes().stream().filter(it -> it.getFormulas().contains(contract.getWarrantyFormula())).findFirst().get();
	}

	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item instanceof Contract || item instanceof Sinister) {
			refreshDetails();
		}
	}

	@Inject
	@Optional
	private void itemEdited(@UIEventTopic(IApplicationEvent.ITEM_EDITED) final Object item) {
	}

	@Inject
	@Optional
	private void itemRemoved(@UIEventTopic(IApplicationEvent.ITEM_REMOVED) final Object item) {
		if (item instanceof Contract || item instanceof Sinister) {
			refreshDetails();
		}
	}
}
