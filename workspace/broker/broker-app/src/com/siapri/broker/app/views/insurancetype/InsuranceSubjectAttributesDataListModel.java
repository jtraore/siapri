package com.siapri.broker.app.views.insurancetype;

import java.util.ArrayList;

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
import com.siapri.broker.business.model.InsuranceSubjectAttribute;
import com.siapri.broker.business.model.InsuranceType;

public class InsuranceSubjectAttributesDataListModel extends DataListModel {
	
	private final InsuranceType insuranceType;
	
	public InsuranceSubjectAttributesDataListModel(final Composite parent, final InsuranceType insuranceType) {
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
			final InsuranceSubjectAttribute element = new InsuranceSubjectAttribute();
			final String title = "Nouvelle propriété";
			final String description = String.format("Cette fenêtre permet de créer une nouvelle propriété");
			final CodeDescriptionPairCustomizer<InsuranceSubjectAttribute> customizer = new CodeDescriptionPairCustomizer<>(element, title, description);
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				insuranceType.getAttributes().add(element);
				return element;
			}
			return null;
		};
		
		final IAction editAction = (event) -> {
			final InsuranceSubjectAttribute element = (InsuranceSubjectAttribute) event.getTarget();
			final String title = "Edition d'une propriété";
			final String description = String.format("Cette fenêtre permet d'éditer une propriété");
			final CodeDescriptionPairCustomizer<InsuranceSubjectAttribute> customizer = new CodeDescriptionPairCustomizer<>(element, title, description);
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				return element;
			}
			return null;
		};
		
		final IAction deleteAction = (event) -> {
			final InsuranceSubjectAttribute element = (InsuranceSubjectAttribute) event.getTarget();
			insuranceType.getAttributes().remove(element);
			return element;
		};
		
		actionModel = new DataListActionModel(createAction, editAction, deleteAction);
		
		dataList = new WritableList<Object>(new ArrayList<>(insuranceType.getAttributes()), InsuranceSubjectAttribute.class) {
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
			final InsuranceSubjectAttribute warranty = (InsuranceSubjectAttribute) object;
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
