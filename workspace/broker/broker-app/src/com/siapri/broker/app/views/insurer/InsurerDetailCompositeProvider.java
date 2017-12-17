package com.siapri.broker.app.views.insurer;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.app.views.insurancetype.InsuranceTypeOverviewItemLocator;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.InsuranceType;

public class InsurerDetailCompositeProvider extends AbstractDetailCompositeProvider<Company> {

	private final Map<Company, InsurerDetail> insurerDetails;
	
	public InsurerDetailCompositeProvider(final String id, final Map<Company, InsurerDetail> insurerDetails) {
		super(id);
		this.insurerDetails = insurerDetails;
	}
	
	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Company && ((Company) item).isInsurer();
	}
	
	@Override
	public Composite createComposite(final Composite parent, final Company item) {
		
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(2, true);
		layout.horizontalSpacing = 25;
		composite.setLayout(layout);

		createInsurerComposite(composite, item);
		createContractComposite(parent, item);
		createSinisterComposite(parent, item);

		return composite;
	}
	
	private void createInsurerComposite(final Composite parent, final Company item) {
		createCompanyComposite(parent, item, false);
	}
	
	private void createContractComposite(final Composite parent, final Company item) {
		createListComponent(parent, insurerDetails.get(item).getContractNumberPerInsurance(), "Nombre de contrats par type d'assurance");
	}
	
	private void createSinisterComposite(final Composite parent, final Company item) {
		createListComponent(parent, insurerDetails.get(item).getSinisterNumberPerInsurance(), "Nombre de sinistres par type d'assurance");
	}
	
	private void createListComponent(final Composite parent, final Map<InsuranceType, Integer> lst, final String title) {

		final StringBuffer stringBuffer = new StringBuffer(title);
		stringBuffer.append("\n\n");

		final Composite composite = createColumnComposite(parent);
		final StyledText control = new StyledText(composite, SWT.WRAP | SWT.MULTI);
		control.setEditable(false);
		control.setLayoutData(new GridData(GridData.FILL_BOTH));

		final InsuranceTypeOverviewItemLocator locator = new InsuranceTypeOverviewItemLocator();
		
		lst.forEach((insuranceType, count) -> {
			stringBuffer.append(String.format("%s : %d\n", insuranceType.getName(), count));
		});

		control.setText(stringBuffer.toString());
		
		control.setStyleRange(Util.createStyleRange(control.getText(), title, SWT.BOLD));

		lst.forEach((insuranceType, count) -> {
			final String styleData = insuranceType.getName();
			control.setStyleRange(Util.createStyleRange(control.getText(), insuranceType.getName(), SWT.BOLD | SWT.ITALIC, styleData));
			addLocationListener(locator, insuranceType, control, styleData);
		});
	}
	
}
