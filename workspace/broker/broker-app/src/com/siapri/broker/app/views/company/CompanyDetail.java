package com.siapri.broker.app.views.company;

import com.siapri.broker.business.model.Company;

public class CompanyDetail {
	
	private Company company;
	
	public CompanyDetail(final Company company) {
		this.company = company;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(final Company company) {
		this.company = company;
	}
	
}
