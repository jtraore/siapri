package com.siapri.broker.app.views.common.datalist;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.action.ContextualAction;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.customizer.DocumentList;
import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.business.model.AbstractDocumentProvider;
import com.siapri.broker.business.model.AbstractEntity;
import com.siapri.broker.business.service.IBasicDaoService;

public abstract class DataListModel<T> {

	protected String title;
	protected WritableList<Object> dataList;
	protected ITableLabelProvider labelProvider;
	protected ColumnDescriptor[] columnDescriptors;
	protected DataListActionModel actionModel;
	protected String[] xPathExpressions;
	protected boolean filterDisplayed = true;
	protected boolean reportButtonDisplayed = true;
	protected boolean selectionEventActivated = true;
	
	public DataListModel() {
		this(true);
	}

	public DataListModel(final boolean initialize) {
		if (initialize) {
			initialize();
		}
	}
	
	// public DataListModel(final WritableList<Object> dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel) {
	// this(dataList, labelProvider, columnDescriptors, actionModel, null);
	// }
	//
	// public DataListModel(final WritableList<Object> dataList, final ITableLabelProvider labelProvider, final ColumnDescriptor[] columnDescriptors, final DataListActionModel actionModel, final
	// String[] xPathExpressions) {
	// this.dataList = dataList;
	// this.labelProvider = labelProvider;
	// this.columnDescriptors = columnDescriptors;
	// this.actionModel = actionModel;
	// this.xPathExpressions = xPathExpressions;
	// }

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public WritableList<Object> getDataList() {
		return dataList;
	}

	public ITableLabelProvider getLabelProvider() {
		return labelProvider;
	}

	public final ColumnDescriptor[] getColumnDescriptors() {
		return columnDescriptors;
	}

	public final DataListActionModel getActionModel() {
		return actionModel;
	}

	public String[] getXPathExpressions() {
		return xPathExpressions;
	}

	public void setxPathExpressions(final String[] xPathExpressions) {
		this.xPathExpressions = xPathExpressions;
	}

	public void setDataList(final WritableList<Object> dataList) {
		this.dataList = dataList;
	}

	public void setLabelProvider(final ITableLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public void setColumnDescriptors(final ColumnDescriptor[] columnDescriptors) {
		this.columnDescriptors = columnDescriptors;
	}

	public void setActionModel(final DataListActionModel actionModel) {
		this.actionModel = actionModel;
	}

	public Object getElementType() {
		return dataList.getElementType();
	}

	public boolean isFilterDisplayed() {
		return filterDisplayed;
	}

	public void setFilterDisplayed(final boolean filterDisplayed) {
		this.filterDisplayed = filterDisplayed;
	}

	public boolean isReportButtonDisplayed() {
		return reportButtonDisplayed;
	}

	public void setReportButtonDisplayed(final boolean reportButtonDisplayed) {
		this.reportButtonDisplayed = reportButtonDisplayed;
	}
	
	public boolean isSelectionEventActivated() {
		return selectionEventActivated;
	}
	
	public void setSelectionEventActivated(final boolean selectionEventActivated) {
		this.selectionEventActivated = selectionEventActivated;
	}

	protected void initialize() {
		
		actionModel = new DataListActionModel(getCreateAction(), getEditAction(), getDeleteAction(), createDatalistMenuActions());
		
		dataList = new WritableList<>(new ArrayList<>(loadElements()), getObjectClass());
	}

	protected abstract List<T> loadElements();
	
	@SuppressWarnings("unchecked")
	public List<T> getElements() {
		return dataList.stream().map(e -> (T) e).collect(Collectors.toList());
	}

	protected IAction getCreateAction() {
		return event -> {
			final T element = createObject();
			final ICustomizer<T> customizer = createCustomizer(element, getCreateDialogTitle(), getCreateDialogDescription());
			DocumentList documentList = null;
			if (element instanceof AbstractDocumentProvider) {
				documentList = new DocumentList(((AbstractDocumentProvider) element).getDocuments());
			}
			final DialogBox dialog = new CustomizerDialog(Display.getDefault().getActiveShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				return save(element);
			}
			return null;
		};
	}

	@SuppressWarnings("unchecked")
	protected IAction getEditAction() {
		return event -> {
			final T element = (T) event.getTarget();
			final ICustomizer<T> customizer = createCustomizer(element, getEditDialogTitle(), getEditDialogDescription());
			DocumentList documentList = null;
			if (element instanceof AbstractDocumentProvider) {
				documentList = new DocumentList(((AbstractDocumentProvider) element).getDocuments());
			}
			final CustomizerDialog dialog = new CustomizerDialog(Display.getDefault().getActiveShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				return save(element);
			} else {
				cancelEdit(element);
			}
			return null;
		};
	}
	
	protected void cancelEdit(final T element) {
		//
	}

	@SuppressWarnings("unchecked")
	protected IAction getDeleteAction() {
		return event -> {
			final T element = (T) event.getTarget();
			delete(element);
			return element;
		};
	}
	
	@SuppressWarnings("unchecked")
	protected T save(final T element) {
		if (element instanceof AbstractEntity) {
			return (T) BundleUtil.getService(IBasicDaoService.class).save((AbstractEntity) element);
		}
		return element;
	}
	
	protected void delete(final T element) {
		if (element instanceof AbstractEntity) {
			BundleUtil.getService(IBasicDaoService.class).delete((AbstractEntity) element);
		}
	}

	protected ContextualAction[] createDatalistMenuActions() {
		return new ContextualAction[0];
	}
	
	protected abstract ICustomizer<T> createCustomizer(T element, String title, String description);
	
	protected String getCreateDialogTitle() {
		return "Nouveau";
	}
	
	protected String getCreateDialogDescription() {
		return "Nouvel élément";
	}
	
	protected String getEditDialogTitle() {
		return "Edition";
	}
	
	protected String getEditDialogDescription() {
		return "Edition d'un élément";
	}

	@SuppressWarnings("unchecked")
	protected T createObject() {
		try {
			return (T) getObjectClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected Class<?> getObjectClass() {
		Class<?> clazz = getClass();
		while (!clazz.getGenericSuperclass().getTypeName().matches(String.format("%s<.*>", DataListModel.class.getName()))) {
			clazz = clazz.getSuperclass();
		}
		try {
			return Class.forName(((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0].getTypeName());
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
