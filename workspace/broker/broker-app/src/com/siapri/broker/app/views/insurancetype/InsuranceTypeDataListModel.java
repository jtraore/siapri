package com.siapri.broker.app.views.insurancetype;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.action.DataListActionEvent;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Person;
import com.siapri.broker.business.service.IBasicDaoService;

public class InsuranceTypeDataListModel extends DataListModel {
	
	private List<InsuranceType> insuranceTypes;
	
	public InsuranceTypeDataListModel(final Composite parent) {
		inititalize(parent);
	}
	
	private void inititalize(final Composite parent) {
		
		labelProvider = new DataListLabelProvider();
		
		xPathExpressions = new String[] { "code", "name" };
		
		columnDescriptors = new ColumnDescriptor[2];
		columnDescriptors[0] = new ColumnDescriptor("Nom", 0.30, 125);
		columnDescriptors[1] = new ColumnDescriptor("Prénom", 0.70, 125);
		
		final IAction createAction = (event) -> {
			final InsuranceType insuranceType = new InsuranceType();
			final String title = "Nouveau type d'assurance";
			final String description = String.format("Cette fenêtre permet de créer un nouveau type d'assurance");
			final InsuranceTypeCustomizer customizer = new InsuranceTypeCustomizer(insuranceType, title, description);
			// final DocumentList documentList = new DocumentList(insuranceType.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer);
			if (dialog.open() == Window.OK) {
				// Save to DB
				final InsuranceType savedClient = BundleUtil.getService(IBasicDaoService.class).save(insuranceType);
				((DataListActionEvent) event).getDataListModel().getDataList().add(savedClient);
				return savedClient;
			}
			return null;
		};
		
		final IAction editAction = (event) -> {
			final InsuranceType insuranceType = (InsuranceType) event.getTarget();
			final String title = "Edition d'un type d'assurance";
			final String description = String.format("Cette fenêtre permet d'éditer un type d'assurance");
			final InsuranceTypeCustomizer customizer = new InsuranceTypeCustomizer(insuranceType, title, description);
			// final DocumentList documentList = new DocumentList(insuranceType.getDocuments());
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer);
			
			if (dialog.open() == Window.OK) {
				// Merge to DB
				return BundleUtil.getService(IBasicDaoService.class).save(insuranceType);
			}
			return null;
		};
		
		final IAction deleteAction = (event) -> {
			final InsuranceType insuranceType = (InsuranceType) event.getTarget();
			// Delete from DB
			BundleUtil.getService(IBasicDaoService.class).delete(insuranceType);
			((DataListActionEvent) event).getDataListModel().getDataList().remove(insuranceType);
			return insuranceType;
		};
		
		actionModel = new DataListActionModel(createAction, editAction, deleteAction);
		
		insuranceTypes = retrieveInsuranceTypes();
		dataList = new WritableList<Object>(new ArrayList<>(insuranceTypes), Person.class) {
			@Override
			public boolean add(final Object element) {
				return super.add(element);
			}
		};
		
	}
	
	private List<InsuranceType> retrieveInsuranceTypes() {
		return BundleUtil.getService(IBasicDaoService.class).getAll(InsuranceType.class);
	}
	
	public List<InsuranceType> getInsuranceTypes() {
		return insuranceTypes;
	}
	
	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}
		
		@Override
		public String getColumnText(final Object object, final int column) {
			final InsuranceType insuranceType = (InsuranceType) object;
			switch (column) {
				case 0:
					return insuranceType.getCode();
				case 1:
					return insuranceType.getName();
			}
			return null;
		}
	}
}