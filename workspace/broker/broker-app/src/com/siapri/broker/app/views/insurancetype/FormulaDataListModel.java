package com.siapri.broker.app.views.insurancetype;

import java.util.ArrayList;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.views.common.action.DataListActionEvent;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.WarrantyFormula;

public class FormulaDataListModel extends DataListModel {
	
	private final InsuranceType insuranceType;
	
	public FormulaDataListModel(final Composite parent, final InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
		inititalize(parent);
	}
	
	private void inititalize(final Composite parent) {
		
		labelProvider = new DataListLabelProvider();
		
		xPathExpressions = new String[] { "code", "name" };
		
		columnDescriptors = new ColumnDescriptor[2];
		columnDescriptors[0] = new ColumnDescriptor("Code", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Name", 0.70, 125);
		
		final IAction createAction = (event) -> {
			final WarrantyFormula formula = new WarrantyFormula();
			final String title = "Nouvelle formule de garanties";
			final String description = String.format("Cette fenêtre permet de créer une nouvelle fromule de garanties");
			final FormulaCustomizer customizer = new FormulaCustomizer(formula, insuranceType, title, description);
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				insuranceType.getFormulas().add(formula);
				((DataListActionEvent) event).getDataListModel().getDataList().add(formula);
				return formula;
			}
			return null;
		};
		
		final IAction editAction = (event) -> {
			final WarrantyFormula formula = (WarrantyFormula) event.getTarget();
			final String title = "Edition d'une formule garanties";
			final String description = String.format("Cette fenêtre permet d'éditer une formule de garanties");
			final FormulaCustomizer customizer = new FormulaCustomizer(formula, insuranceType, title, description);
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				return formula;
			}
			return null;
		};
		
		final IAction deleteAction = (event) -> {
			final WarrantyFormula formula = (WarrantyFormula) event.getTarget();
			insuranceType.getFormulas().remove(formula);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(formula);
			return formula;
		};
		
		actionModel = new DataListActionModel(createAction, editAction, deleteAction);
		
		dataList = new WritableList<Object>(new ArrayList<>(insuranceType.getFormulas()), WarrantyFormula.class) {
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
			final WarrantyFormula formula = (WarrantyFormula) object;
			switch (column) {
				case 0:
					return formula.getCode();
				case 1:
					return formula.getName();
			}
			return null;
		}
	}
}
