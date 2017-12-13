package com.siapri.broker.app.views.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.siapri.broker.app.views.client.ClientOverviewItemLocator;
import com.siapri.broker.app.views.common.EAddressType;
import com.siapri.broker.app.views.common.EPhoneType;
import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.action.ContextualAction;
import com.siapri.broker.app.views.common.action.ContextualActionPathElement;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.detail.AbstractDetailCompositeProvider;
import com.siapri.broker.app.views.entreprise.EnterpriseOverviewItemLocator;
import com.siapri.broker.app.views.insurancetype.InsuranceTypeOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.app.views.sinister.SinisterDatalistModel;
import com.siapri.broker.app.views.sinister.SinisterOverviewItemLocator;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.model.Warranty;
import com.siapri.broker.business.model.WarrantyFormula;

public class ContractDetailCompositeProvider extends AbstractDetailCompositeProvider<Contract> {

	private final Map<Contract, ContractDetail> contractDetails;
	
	private final Map<WarrantyFormula, InsuranceType> warrantyFormulas;

	private Contract currentContract;
	
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

		currentContract = item;

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

		final String styleData = "InsuranceType";
		
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), String.format("N°%s", item.getNumber()), SWT.BOLD));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), warrantyFormulas.get(item.getWarrantyFormula()).getName(), SWT.BOLD, styleData));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), "Liste des garanties :\n", SWT.BOLD, Display.getCurrent().getSystemColor(SWT.COLOR_RED)));

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
		final Client client = item.getClient();
		if (client instanceof Person) {
			createPersonComposite(parent, (Person) client);
		} else {
			createEntrpriseComposite(parent, (Company) client);
		}
	}

	private void createPersonComposite(final Composite parent, final Person person) {
		final Composite composite = createColumnComposite(parent);
		
		final StyledText clientControl = new StyledText(composite, SWT.WRAP);
		clientControl.setEditable(false);
		
		// @formatter:off
		clientControl.setText(String.format("%s %s %s, né(e) le %s\nAdresse domicile : %s,\nTél. : %s",
						Util.getGenderAsString(person.getGender()),
						person.getFirstName(),
						person.getLastName(),
						Util.DATE_TIME_FORMATTER.format(person.getBirthdate()),
						Util.formatAddress(person.getAddresses().get(EAddressType.HOME.name())),
						person.getPhones().get(EPhoneType.MOBILE.name())));
		// @formatter:on
		clientControl.setLayoutData(new GridData(GridData.FILL_BOTH));

		final String styleData = "Client";
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), String.format("%s %s", person.getFirstName(), person.getLastName()), SWT.BOLD, styleData));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), "Adresse domicile", SWT.BOLD));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), "Tél.", SWT.BOLD));

		clientControl.addListener(SWT.MouseDown, event -> {
			// if ((event.stateMask & SWT.MOD1) != 0) {
			final int offset = clientControl.getOffsetAtLocation(new Point(event.x, event.y));
			final StyleRange selectedStyleRange = clientControl.getStyleRangeAtOffset(offset);
			if (selectedStyleRange != null && styleData.equals(selectedStyleRange.data)) {
				new ClientOverviewItemLocator().locate(new OverviewItem<>(person, ""));
			}
			// }
		});
	}
	
	private void createEntrpriseComposite(final Composite parent, final Company company) {
		final Composite composite = createColumnComposite(parent);

		final StyledText clientControl = new StyledText(composite, SWT.WRAP);
		clientControl.setEditable(false);

		// @formatter:off
			clientControl.setText(String.format("%s - %s\nAdresse : %s,\nTél. : %s",
							company.getSiret(),
							company.getName(),
							Util.formatAddress(company.getAddresses().get(EAddressType.WORK.name())),
							company.getPhones().get(EPhoneType.LAND.name())));
		// @formatter:on
		clientControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		final String styleData = "Client";
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), String.format("%s - %s", company.getSiret(), company.getName()), SWT.BOLD, styleData));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), "Adresse", SWT.BOLD));
		clientControl.setStyleRange(Util.createStyleRange(clientControl.getText(), "Tél.", SWT.BOLD));

		clientControl.addListener(SWT.MouseDown, event -> {
			// if ((event.stateMask & SWT.MOD1) != 0) {
			final int offset = clientControl.getOffsetAtLocation(new Point(event.x, event.y));
			final StyleRange selectedStyleRange = clientControl.getStyleRangeAtOffset(offset);
			if (selectedStyleRange != null && styleData.equals(selectedStyleRange.data)) {
				new EnterpriseOverviewItemLocator().locate(new OverviewItem<>(company, ""));
			}
			// }
		});
	}

	private void createSinisterComposite(final Composite parent, final Contract item) {
		final Composite composite = createColumnComposite(parent);
		new TitledSeparator(composite, "Liste des sinistres").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final SinisterDatalistModel dataListModel = new ContractSinisterDataListModel(composite);
		dataListModel.setFilterDisplayed(false);
		dataListModel.setReportButtonDisplayed(false);
		final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		dataListComposite.setLayoutData(gridData);
	}
	
	private Composite createColumnComposite(final Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		columnComposite.setLayout(new GridLayout());
		return columnComposite;
	}

	private class ContractSinisterDataListModel extends SinisterDatalistModel {

		public ContractSinisterDataListModel(final Composite parent) {
			super(parent);
			columnDescriptors = new ColumnDescriptor[] { new ColumnDescriptor("Date", 0.15, 125), new ColumnDescriptor("Contrat", 0.15, 125), new ColumnDescriptor("Adresse", 0.35, 125), new ColumnDescriptor("Description", 0.35, 125) };
			labelProvider = new SinisterDataListLabelProvider();
			selectionEventActivated = false;
		}
		
		@Override
		protected List<Sinister> retrieveSinisters() {
			return contractDetails.get(currentContract).getSinisters();
		}

		@Override
		protected ContextualAction[] createDatalistMenuActions(final Composite parent) {
			final IAction navigateToAction = event -> {
				new SinisterOverviewItemLocator().locate(new OverviewItem<>((Sinister) event.getTarget(), ""));
				return null;
			};

			final ContextualActionPathElement[] navigateToPath = new ContextualActionPathElement[] { new ContextualActionPathElement("Afficher dans la vue Sinistres", null) };

			return new ContextualAction[] { new ContextualAction(navigateToAction, navigateToPath) };
		}
	}

	private final class SinisterDataListLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}
		
		@Override
		public String getColumnText(final Object object, final int column) {
			final Sinister sinister = (Sinister) object;
			switch (column) {
				case 0:
					return Util.DATE_TIME_FORMATTER.format(sinister.getOccuredDate());
				case 1:
					return sinister.getContract().getNumber();
				case 2:
					return Util.formatAddress(sinister.getAddress());
				case 3:
					return sinister.getDescription();
			}
			return null;
		}
	}
}
