package com.siapri.broker.app.views.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.siapri.broker.app.views.client.ClientOverviewItemLocator;
import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.app.views.insurancetype.InsuranceTypeOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Warranty;
import com.siapri.broker.business.model.WarrantyFormula;

public class ContractDetailCompositeProvider extends AbstractDetailCompositeProvider<Contract> {

	private final Map<Contract, ContractDetail> contractDetails;
	
	private final Map<WarrantyFormula, InsuranceType> warrantyFormulas;
	
	public ContractDetailCompositeProvider(final String id, final Map<Contract, ContractDetail> contractDetails, final Map<WarrantyFormula, InsuranceType> warrantyFormulas) {
		super(id);
		this.contractDetails = contractDetails;
		this.warrantyFormulas = warrantyFormulas;
	}
	
	@Override
	public boolean canProvide(final Object item) {
		return item instanceof Contract;
	}
	
	@Override
	public Composite createComposite(final Composite parent, final Contract item) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, true);
		layout.horizontalSpacing = 25;
		composite.setLayout(layout);
		
		createGeneralComposite(composite, item);
		createClientComposite(composite, item);
		createSinisterComposite(composite, item);
		
		return composite;
	}

	private void createGeneralComposite(final Composite parent, final Contract item) {
		final Composite composite = createColumnComposite(parent);
		
		final StyledText clientControl = new StyledText(composite, SWT.WRAP);
		clientControl.setEditable(false);
		
		final Map<String, Warranty> warranties = new HashMap<>();
		warrantyFormulas.forEach((formula, insuranceType) -> {
			insuranceType.getWarranties().forEach(warranty -> warranties.put(warranty.getCode(), warranty));
		});
		
		// @formatter:off
		final List<String> warrantiList = item.getWarrantyFormula().getWarrantyCodes()
				.stream()
				.map(code -> String.format("\t> %s", warranties.get(code).getDescription()))
				.collect(Collectors.toList());
		clientControl.setText(String.format("Contrat N°%s du %s\nAssurance %s\nListe des garanties :\n%s",
						item.getNumber(),
						Util.DATE_TIME_FORMATTER.format(item.getSubscriptionDate()),
						warrantyFormulas.get(item.getWarrantyFormula()).getName(),
						String.join("\n", warrantiList)));
		// @formatter:on
		clientControl.setLayoutData(new GridData(GridData.FILL_BOTH));

		StyleRange styleRange = new StyleRange();
		styleRange.start = "Contrat ".length();
		styleRange.length = String.format("N°%s", item.getNumber()).length();
		styleRange.fontStyle = SWT.BOLD;
		clientControl.setStyleRange(styleRange);
		
		int index = styleRange.start + styleRange.length;
		
		styleRange = new StyleRange();
		styleRange.start = String.format(" du %s\nAssurance ", Util.DATE_TIME_FORMATTER.format(item.getSubscriptionDate())).length() + index;
		styleRange.length = warrantyFormulas.get(item.getWarrantyFormula()).getName().length();
		styleRange.fontStyle = SWT.BOLD;
		styleRange.underline = true;
		styleRange.underlineStyle = SWT.UNDERLINE_LINK;
		final String styleData = "InsuranceType";
		styleRange.data = styleData;
		clientControl.setStyleRange(styleRange);

		index = styleRange.start + styleRange.length;
		
		styleRange = new StyleRange();
		styleRange.start = index;
		styleRange.length = "Liste des garanties :\n".length();
		styleRange.fontStyle = SWT.BOLD;
		styleRange.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		clientControl.setStyleRange(styleRange);
		
		clientControl.addListener(SWT.MouseDown, event -> {
			// if ((event.stateMask & SWT.MOD1) != 0) {
			final int offset = clientControl.getOffsetAtLocation(new Point(event.x, event.y));
			final StyleRange selectedStyleRange = clientControl.getStyleRangeAtOffset(offset);
			if (selectedStyleRange != null && styleData.equals(selectedStyleRange.data)) {
				new InsuranceTypeOverviewItemLocator().locate(new OverviewItem<>(warrantyFormulas.get(item.getWarrantyFormula()), ""));
			}
			// }
		});
	}

	private void createClientComposite(final Composite parent, final Contract item) {
		final Composite composite = createColumnComposite(parent);
		
		final StyledText clientControl = new StyledText(composite, SWT.WRAP);
		clientControl.setEditable(false);
		
		final Person client = (Person) item.getClient();
		// @formatter:off
		clientControl.setText(String.format("%s %s %s, né(e) le %s\nAdresse domicile : %s,\nTél. : %s",
						Util.getGenderAsString(client.getGender()),
						client.getFirstName(),
						client.getLastName(),
						Util.DATE_TIME_FORMATTER.format(client.getBirthdate()),
						Util.formatAddress(client.getAddresses().get(EAddressType.HOME.name())),
						client.getPhones().get(EPhoneType.MOBILE.name())));
		// @formatter:on
		clientControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		StyleRange styleRange = new StyleRange();
		styleRange.start = Util.getGenderAsString(client.getGender()).length() + 1;
		styleRange.length = String.format("%s %s", client.getFirstName(), client.getLastName()).length();
		styleRange.underline = true;
		styleRange.underlineStyle = SWT.UNDERLINE_LINK;
		final String styleData = "Client";
		styleRange.data = styleData;
		styleRange.fontStyle = SWT.BOLD;
		clientControl.setStyleRange(styleRange);

		int index = styleRange.start + styleRange.length;
		
		styleRange = new StyleRange();
		styleRange.start = String.format(", né(e) le %s\n", Util.DATE_TIME_FORMATTER.format(client.getBirthdate())).length() + index;
		styleRange.length = "Adresse domicile".length();
		styleRange.fontStyle = SWT.BOLD;
		clientControl.setStyleRange(styleRange);

		index = styleRange.start + styleRange.length;

		styleRange = new StyleRange();
		styleRange.start = String.format(" : %s,\n", Util.formatAddress(client.getAddresses().get(EAddressType.HOME.name()))).length() + index;
		styleRange.length = "Tél.".length();
		styleRange.fontStyle = SWT.BOLD;
		clientControl.setStyleRange(styleRange);

		clientControl.addListener(SWT.MouseDown, event -> {
			// if ((event.stateMask & SWT.MOD1) != 0) {
			final int offset = clientControl.getOffsetAtLocation(new Point(event.x, event.y));
			final StyleRange selectedStyleRange = clientControl.getStyleRangeAtOffset(offset);
			if (selectedStyleRange != null && styleData.equals(selectedStyleRange.data)) {
				new ClientOverviewItemLocator().locate(new OverviewItem<>(client, ""));
			}
			// }
		});
	}

	private void createSinisterComposite(final Composite parent, final Contract item) {
		final Composite composite = createColumnComposite(parent);
	}
	
	private Composite createColumnComposite(final Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		columnComposite.setLayout(new GridLayout());
		return columnComposite;
	}
}
