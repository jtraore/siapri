package com.siapri.broker.app.views.insurer;

import java.util.List;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.company.CompanyOverviewGroupProvider;
import com.siapri.broker.app.views.overview.IOverviewItemLocator;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.service.IBasicDaoService;

public class InsurerOverviewGroupProvider extends CompanyOverviewGroupProvider {

	@Override
	public String getTitle() {
		return "Derniers assureurs enrégistrés";
	}

	@Override
	public IOverviewItemLocator<Company> getItemLocator() {
		return new InsurerOverviewItemLocator();
	}

	@Override
	protected List<Company> getOverviewCompanies() {
		return BundleUtil.getService(IBasicDaoService.class).getInsurers(10);
	}

}
