package com.siapri.broker.app.views.entreprise;

import java.util.List;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.company.CompanyOverviewGroupProvider;
import com.siapri.broker.app.views.overview.IOverviewItemLocator;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class EnterpriseOverviewGroupProvider extends CompanyOverviewGroupProvider {

	@Override
	public String getTitle() {
		return "Dernières entreprises enrégistrées";
	}

	@Override
	public IOverviewItemLocator<Company> getItemLocator() {
		return new EnterpriseOverviewItemLocator();
	}

	@Override
	protected List<Company> getOverviewCompanies() {
		return BundleUtil.getService(DaoCacheService.class).getEntreprises(10);
	}

}
