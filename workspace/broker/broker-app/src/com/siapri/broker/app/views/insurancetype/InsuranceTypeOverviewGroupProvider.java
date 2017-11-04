package com.siapri.broker.app.views.insurancetype;

import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.overview.IOverviewGroupProvider;
import com.siapri.broker.app.views.overview.IOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.service.IBasicDaoService;

public class InsuranceTypeOverviewGroupProvider implements IOverviewGroupProvider<InsuranceType> {

	@Override
	public String getTitle() {
		return "Derniers types d'assurance enregistrés";
	}

	@Override
	public List<OverviewItem<InsuranceType>> getOverviewItems() {
		return getOverviewInsuranceTypes().stream().map(insuranceType -> createOverviewItem(insuranceType)).collect(Collectors.toList());
	}

	@Override
	public IOverviewItemLocator<InsuranceType> getItemLocator() {
		return new InsuranceTypeOverviewItemLocator();
	}

	public static OverviewItem<InsuranceType> createOverviewItem(final InsuranceType insuranceType) {
		return new OverviewItem<>(insuranceType, insuranceType.getCode() + " " + insuranceType.getName());
	}

	protected List<InsuranceType> getOverviewInsuranceTypes() {
		return BundleUtil.getService(IBasicDaoService.class).getLatestElements(InsuranceType.class, 10);
	}
}
