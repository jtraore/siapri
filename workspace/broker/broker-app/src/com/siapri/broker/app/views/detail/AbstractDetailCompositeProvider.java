package com.siapri.broker.app.views.detail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.ITableLabelProvider;
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
import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.action.ContextualAction;
import com.siapri.broker.app.views.common.action.ContextualActionPathElement;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.common.datalist.DataListItemLocator;
import com.siapri.broker.app.views.contract.ContractDataListModel;
import com.siapri.broker.app.views.contract.ContractOverviewItemLocator;
import com.siapri.broker.app.views.entreprise.EnterpriseOverviewItemLocator;
import com.siapri.broker.app.views.insurancetype.InsuranceTypeOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;
import com.siapri.broker.app.views.sinister.SinisterDatalistModel;
import com.siapri.broker.app.views.sinister.SinisterOverviewItemLocator;
import com.siapri.broker.business.model.AbstractEntity;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.model.Sinister;
import com.siapri.broker.business.model.Warranty;
import com.siapri.broker.business.model.WarrantyFormula;

public abstract class AbstractDetailCompositeProvider<T> implements IDetailCompositeProvider<T> {
	
	private String id;
	
	public AbstractDetailCompositeProvider(final String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(final String id) {
		this.id = id;
	}
	
	protected void createPersonComposite(final Composite parent, final Person item, final boolean navigationEnabled) {
		final Composite composite = createColumnComposite(parent);

		final StyledText control = new StyledText(composite, SWT.WRAP);
		control.setEditable(false);

		// @formatter:off
		control.setText(String.format("%s %s %s, né(e) le %s\nAdresse domicile : %s,\nTél. : %s",
						item.getGender().getLabel(),
						item.getFirstName(),
						item.getLastName(),
						Util.DATE_FORMATTER.format(item.getBirthdate()),
						Util.formatAddress(item.getAddresses().get(EAddressType.HOME.name())),
						item.getPhones().get(EPhoneType.MOBILE.name())));
		// @formatter:on
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		if (navigationEnabled) {
			final String styleData = "Client";
			control.setStyleRange(Util.createStyleRange(control.getText(), String.format("%s %s", item.getFirstName(), item.getLastName()), SWT.BOLD, styleData));
			addLocationListener(new ClientOverviewItemLocator(), item, control, styleData);
		} else {
			control.setStyleRange(Util.createStyleRange(control.getText(), String.format("%s %s", item.getFirstName(), item.getLastName()), SWT.BOLD));
		}
		control.setStyleRange(Util.createStyleRange(control.getText(), "Adresse domicile", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Tél.", SWT.BOLD));
	}

	protected void createCompanyComposite(final Composite parent, final Company item, final boolean navigationEnabled) {
		final Composite composite = createColumnComposite(parent);
		final StyledText control = new StyledText(composite, SWT.WRAP | SWT.MULTI);
		control.setEditable(false);
		// @formatter:off
		control.setText(String.format("%s - %s\nActivité : %s\nAdresse : %s,\nTél. : %s",
						item.getSiret(),
						item.getName(),
						item.getActivity(),
						Util.formatAddress(item.getAddresses().get(EAddressType.WORK.name())),
						item.getPhones().get(EPhoneType.LAND.name())));
		// @formatter:on
		control.setLayoutData(new GridData(GridData.FILL_BOTH));

		if (navigationEnabled) {
			final String styleData = "Client";
			control.setStyleRange(Util.createStyleRange(control.getText(), String.format("%s - %s", item.getSiret(), item.getName()), SWT.BOLD | SWT.ITALIC, styleData));
			addLocationListener(new EnterpriseOverviewItemLocator(), item, control, styleData);
		} else {
			control.setStyleRange(Util.createStyleRange(control.getText(), String.format("%s - %s", item.getSiret(), item.getName()), SWT.BOLD | SWT.ITALIC));
		}
		control.setStyleRange(Util.createStyleRange(control.getText(), "Activité", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Adresse", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Tél.", SWT.BOLD));
	}

	protected void createContractComposite(final Composite parent, final Contract item, final Map<WarrantyFormula, InsuranceType> warrantyFormulas, final boolean navigationEnabled) {
		final Composite composite = createColumnComposite(parent);

		final StyledText control = new StyledText(composite, SWT.WRAP);
		control.setEditable(false);

		final Map<String, Warranty> warranties = new HashMap<>();
		warrantyFormulas.forEach((formula, insuranceType) -> {
			insuranceType.getWarranties().forEach(warranty -> warranties.put(warranty.getCode(), warranty));
		});

		// @formatter:off
		final List<String> warrantiList = item.getWarrantyFormula().getWarrantyCodes()
				.stream()
				.map(code -> String.format("\t> %s", warranties.get(code).getDescription()))
				.collect(Collectors.toList());
		control.setText(String.format("Contrat N°%s du %s\nAssurance %s\nListe des garanties :\n%s",
						item.getNumber(),
						Util.DATE_FORMATTER.format(item.getSubscriptionDate()),
						warrantyFormulas.get(item.getWarrantyFormula()).getName(),
						String.join("\n", warrantiList)));
		// @formatter:on
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		if (navigationEnabled) {
			final String styleData = "contract";
			control.setStyleRange(Util.createStyleRange(control.getText(), String.format("N°%s", item.getNumber()), SWT.BOLD, styleData));
			addLocationListener(new ContractOverviewItemLocator(), item, control, styleData);
		} else {
			control.setStyleRange(Util.createStyleRange(control.getText(), String.format("N°%s", item.getNumber()), SWT.BOLD));
		}
		final String insuranceTypeStyleData = "InsuranceType";
		control.setStyleRange(Util.createStyleRange(control.getText(), warrantyFormulas.get(item.getWarrantyFormula()).getName(), SWT.BOLD, insuranceTypeStyleData));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Liste des garanties :\n", SWT.BOLD, Display.getCurrent().getSystemColor(SWT.COLOR_RED)));
		
		addLocationListener(new InsuranceTypeOverviewItemLocator(), warrantyFormulas.get(item.getWarrantyFormula()), control, insuranceTypeStyleData);
	}

	protected void createSinisterComposite(final Composite parent, final Sinister item, final boolean navigationEnabled) {
		final Composite composite = createColumnComposite(parent);
		final StyledText control = new StyledText(composite, SWT.WRAP | SWT.MULTI);
		control.setEditable(false);
		// @formatter:off
		control.setText(String.format("Numéro : %s\nDate : %s\nDescription : %s\nAdresse : %s",
						item.getNumber(),
						Util.DATE_FORMATTER.format(item.getOccuredDate()),
						item.getDescription(),
						Util.formatAddress(item.getAddress())));
		// @formatter:on
		control.setLayoutData(new GridData(GridData.FILL_BOTH));

		if (navigationEnabled) {
			final String styleData = "Sinister";
			control.setStyleRange(Util.createStyleRange(control.getText(), item.getNumber(), SWT.BOLD | SWT.ITALIC, styleData));
			addLocationListener(new SinisterOverviewItemLocator(), item, control, styleData);
		} else {
			control.setStyleRange(Util.createStyleRange(control.getText(), item.getNumber(), SWT.BOLD | SWT.ITALIC));
		}
		control.setStyleRange(Util.createStyleRange(control.getText(), "Numéro", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Date", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Description", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Adresse", SWT.BOLD));
	}

	protected <EntityType extends AbstractEntity> void addLocationListener(final DataListItemLocator<EntityType> locator, final EntityType item, final StyledText control, final String styleData) {
		control.addListener(SWT.MouseDown, event -> {
			try {
				// if ((event.stateMask & SWT.MOD1) != 0) {
				final int offset = control.getOffsetAtLocation(new Point(event.x, event.y));
				final StyleRange selectedStyleRange = control.getStyleRangeAtOffset(offset);
				if (selectedStyleRange != null && styleData.equals(selectedStyleRange.data)) {
					locator.locate(new OverviewItem<>(item, ""));
				}
				// }
			} catch (final Exception e) {
			}
		});
	}
	
	protected Composite createColumnComposite(final Composite parent) {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		columnComposite.setLayout(new GridLayout());
		return columnComposite;
	}

	protected void createContractListComposite(final Composite parent, final ITableLabelProvider labelProvider) {
		final Composite composite = createColumnComposite(parent);
		new TitledSeparator(composite, "Liste des contrats").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		
		final ContractDataListModel dataListModel = new ContractDetailDataListModel(composite, labelProvider);
		dataListModel.setFilterDisplayed(false);
		dataListModel.setReportButtonDisplayed(false);
		final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		dataListComposite.setLayoutData(gridData);
	}
	
	protected void createSinisterListComposite(final Composite parent, final ITableLabelProvider labelProvider) {
		final Composite composite = createColumnComposite(parent);
		new TitledSeparator(composite, "Liste des sinistres").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));

		final SinisterDatalistModel dataListModel = new SinisterDetailDataListModel(composite, labelProvider);
		dataListModel.setFilterDisplayed(false);
		dataListModel.setReportButtonDisplayed(false);
		final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		final GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		dataListComposite.setLayoutData(gridData);
	}

	protected List<Contract> getContextContracts() {
		return new ArrayList<>();
	}
	
	protected List<Sinister> getContextSinisters() {
		return new ArrayList<>();
	}

	private class ContractDetailDataListModel extends ContractDataListModel {
		
		public ContractDetailDataListModel(final Composite parent, final ITableLabelProvider labelProvider) {
			super(parent);
			columnDescriptors = new ColumnDescriptor[] { new ColumnDescriptor("Number", 0.15, 125), new ColumnDescriptor("Date", 0.15, 125), new ColumnDescriptor("Assurance", 0.60, 125) };
			this.labelProvider = labelProvider;
			selectionEventActivated = false;
		}

		@Override
		protected List<Contract> retrieveContracts() {
			return getContextContracts();
		}
		
		@Override
		protected ContextualAction[] createDatalistMenuActions(final Composite parent) {
			final IAction navigateToAction = event -> {
				new ContractOverviewItemLocator().locate(new OverviewItem<>((Contract) event.getTarget(), ""));
				return null;
			};
			
			final ContextualActionPathElement[] navigateToPath = new ContextualActionPathElement[] { new ContextualActionPathElement("Afficher dans la vue Contrats", null) };
			
			return new ContextualAction[] { new ContextualAction(navigateToAction, navigateToPath) };
		}
	}

	private class SinisterDetailDataListModel extends SinisterDatalistModel {

		public SinisterDetailDataListModel(final Composite parent, final ITableLabelProvider labelProvider) {
			super(parent);
			columnDescriptors = new ColumnDescriptor[] { new ColumnDescriptor("Date", 0.15, 125), new ColumnDescriptor("Contrat", 0.15, 125), new ColumnDescriptor("Adresse", 0.35, 125), new ColumnDescriptor("Description", 0.35, 125) };
			this.labelProvider = labelProvider;
			selectionEventActivated = false;
		}
		
		@Override
		protected List<Sinister> retrieveSinisters() {
			return getContextSinisters();
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
}
