package com.siapri.broker.app.views.insurancetype;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.TitledSeparator;
import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.business.model.InsuranceType;

public class InsuranceTypeCustomizer extends AbstractCustomizer<InsuranceType> {

	private final InsuranceTypeCustomizerModel customizerModel;
	
	public InsuranceTypeCustomizer(final InsuranceType insuranceType, final String title, final String description) {
		super(insuranceType, title, description);
		customizerModel = ProxyFactory.createProxy(new InsuranceTypeCustomizerModel(insuranceType));
	}
	
	@Override
	public Composite createArea(final Composite parent, final int style) {
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(6, false);
		composite.setLayout(gridLayout);

		final Label codeLabel = new Label(composite, SWT.NONE);
		codeLabel.setText("Code: ");
		final Text codeText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "code", codeText, IValidationSupport.NON_EMPTY_VALIDATOR);
		codeText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 2, 1));

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));
		
		final Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Nom: ");
		final Text nameText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "name", nameText, IValidationSupport.NON_EMPTY_VALIDATOR);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 5, 1));
		
		new TitledSeparator(composite, "Liste des garanties").setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 6, 1));

		final WarrantyDataListModel dataListModel = new WarrantyDataListModel(composite, object);
		final DataListComposite dataListComposite = new DataListComposite(composite, SWT.NONE, dataListModel);
		final GridData warrantiGridData = new GridData(GridData.FILL, GridData.FILL, true, true, 6, 1);
		warrantiGridData.heightHint = 500;
		dataListComposite.setLayoutData(warrantiGridData);

		return composite;
	}
	
	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}
	
	@Override
	public void cancelUpdate() {
	}

}
