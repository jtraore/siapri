package com.siapri.broker.app.views.insurancetype;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Warranty;

public class WarrantyDataListModel extends DataListModel {

	private final InsuranceType insuranceType;

	public WarrantyDataListModel(final Composite parent, final InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		labelProvider = new DataListLabelProvider();

		xPathExpressions = new String[] { "code", "description" };

		columnDescriptors = new ColumnDescriptor[2];
		columnDescriptors[0] = new ColumnDescriptor("Code", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Description", 0.70, 125);

		final IAction createAction = (event) -> {
			final Warranty warranty = new Warranty();
			final String title = "Nouvelle garantie";
			final String description = String.format("Cette fenêtre permet de créer une nouvelle garantie");
			final WarrantyCustomizer customizer = new WarrantyCustomizer(warranty, title, description);
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				insuranceType.getWarranties().add(warranty);
				return warranty;
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Warranty warranty = (Warranty) event.getTarget();
			final String oldCode = warranty.getCode();
			final String title = "Edition d'une garantie";
			final String description = String.format("Cette fenêtre permet d'éditer une garantie");
			final WarrantyCustomizer customizer = new WarrantyCustomizer(warranty, title, description);
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer);

			if (dialog.open() == Window.OK) {
				// Update the code in the warranty formulas
				if (!oldCode.equals(warranty.getCode())) {
					insuranceType.getFormulas().forEach(formula -> {
						final List<String> warrantyCodes = formula.getWarrantyCodes();
						final int index = warrantyCodes.indexOf(oldCode);
						if (index >= 0) {
							warrantyCodes.remove(index);
							warrantyCodes.add(warranty.getCode());
						}
					});
				}
				return warranty;
			}
			return null;
		};

		final IAction deleteAction = (event) -> {
			final Warranty warranty = (Warranty) event.getTarget();
			insuranceType.getWarranties().remove(warranty);
			// Update the code from all the warranty formulas it used
			insuranceType.getFormulas().forEach(formula -> formula.getWarrantyCodes().remove(warranty.getCode()));
			return warranty;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction);

		dataList = new WritableList<Object>(new ArrayList<>(insuranceType.getWarranties()), Warranty.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};

	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Warranty warranty = (Warranty) object;
			switch (column) {
				case 0:
					return warranty.getCode();
				case 1:
					return warranty.getDescription();
			}
			return null;
		}
	}
}
