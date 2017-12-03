package com.siapri.broker.app.views.company;

import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.views.overview.IOverviewGroupProvider;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.business.model.Company;

public abstract class CompanyOverviewGroupProvider implements IOverviewGroupProvider<Company> {
	
	@Override
	public List<OverviewItem<Company>> getOverviewItems() {
		return getOverviewCompanies().stream().map(company -> createOverviewItem(company)).collect(Collectors.toList());
	}
	
	public static OverviewItem<Company> createOverviewItem(final Company company) {
		return new OverviewItem<>(company, company.getName());
	}
	
	protected abstract List<Company> getOverviewCompanies();
}
