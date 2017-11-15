package com.siapri.broker.app.views.common.datalist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.E4Service;
import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.common.EImage;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.action.ActionEvent;
import com.siapri.broker.app.views.common.action.ContextualAction;
import com.siapri.broker.app.views.common.action.ContextualActionPathElement;
import com.siapri.broker.app.views.common.action.DataListActionEvent;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.business.model.AbstractDocumentProvider;
import com.siapri.broker.business.model.Document;
import com.siapri.broker.business.service.IBasicDaoService;

public class DataListComposite extends Composite {
	
	private class ReportListener extends SelectionAdapter {
		
		private final Menu menu;
		
		public ReportListener() {
			menu = new Menu(getShell());
			buildContextualActions(menu, dataListModel.getActionModel().getReportActions(), null, null);
		}
		
		@Override
		public void widgetSelected(final SelectionEvent e) {
			final ToolItem item = (ToolItem) e.widget;
			final Rectangle rect = item.getBounds();
			final Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
			menu.setLocation(pt.x, pt.y + rect.height);
			menu.setVisible(true);
		}
	}
	
	private static final class TableResizeController extends ControlAdapter {
		private final ColumnDescriptor[] columnDescriptors;
		
		public TableResizeController(final ColumnDescriptor[] columnDescriptors) {
			this.columnDescriptors = columnDescriptors;
		}
		
		@Override
		public void controlResized(final ControlEvent e) {
			final Table table = (Table) e.getSource();
			int widthCumul = 0;
			for (int i = 0; i < table.getColumnCount(); i++) {
				final int columnWidth = (int) (columnDescriptors[i].getColumnWeight() * table.getSize().x);
				if (i < table.getColumnCount() - 1) {
					table.getColumn(i).setWidth(columnWidth);
					widthCumul += columnWidth;
				} else {
					table.getColumn(i).setWidth(table.getSize().x - widthCumul - 5);
				}
			}
		}
	}

	private Object selectedItem;
	
	private TableViewer tableViewer;
	
	private DataListFilter dataListFilter;
	
	private DataListModel dataListModel = null;
	
	private TableResizeController tableResizeController = null;
	
	private Button deleteButton;
	
	private Button editButton;
	
	private Button addButton;
	
	private final SelectionAdapter createActionController = new SelectionAdapter() {
		@Override
		public void widgetSelected(final SelectionEvent e) {
			final DataListActionEvent event = new DataListActionEvent(e.getSource(), dataListModel);
			final Object result = dataListModel.getActionModel().getCreateAction().execute(event);
			if (result != null) {
				dataListModel.getDataList().add(result);
				BundleUtil.getService(E4Service.class).getEventBroker().post(IApplicationEvent.ITEM_CREATED, result);
				tableViewer.setSelection(new StructuredSelection(result));
			}
			tableViewer.refresh();
		}
	};
	
	private final SelectionAdapter editActionController = new SelectionAdapter() {
		@Override
		public void widgetSelected(final SelectionEvent e) {
			performEdit();
		}
	};
	
	private final SelectionAdapter deleteActionController = new SelectionAdapter() {
		@Override
		public void widgetSelected(final SelectionEvent e) {
			final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
			if (selection.isEmpty()) {
				return;
			}
			final MessageDialog dialog = new MessageDialog(DataListComposite.this.getShell(), "Suppression", null, "Voulez-vous vraiment supprimer cet objet ?", MessageDialog.CONFIRM, new String[] { "Oui", "Non" }, 0);
			if (dialog.open() == Window.CANCEL) {
				return;
			}
			final Object selectedObject = selection.getFirstElement();
			final DataListActionEvent event = new DataListActionEvent(e.getSource(), selectedObject, dataListModel);
			final IAction deleteAction = dataListModel.getActionModel().getDeleteAction();
			if (deleteAction != null) {
				final Object deletedObject = deleteAction.execute(event);
				dataListModel.getDataList().remove(deletedObject);
				tableViewer.refresh();
				BundleUtil.getService(E4Service.class).getEventBroker().post(IApplicationEvent.ITEM_REMOVED, selectedObject);
			}
		}
	};
	
	private final ISelectionChangedListener tableSelectionListener = e -> {
		final boolean dataSelected = !e.getSelectionProvider().getSelection().isEmpty();
		final IAction deleteAction = dataListModel.getActionModel().getDeleteAction();
		if (deleteAction != null) {
			deleteButton.setEnabled(dataSelected);
		} else {
			deleteButton.setEnabled(false);
		}
		final IAction editAction = dataListModel.getActionModel().getEditAction();
		if (editAction != null) {
			editButton.setEnabled(dataSelected);
		} else {
			editButton.setEnabled(false);
		}
		
		if (dataListModel.isSelectionEventActivated()) {
			if (dataSelected) {
				selectedItem = ((IStructuredSelection) e.getSelectionProvider().getSelection()).getFirstElement();
				BundleUtil.getService(E4Service.class).getEventBroker().post(IApplicationEvent.ITEM_SELECTED, selectedItem);
			} else {
				BundleUtil.getService(E4Service.class).getEventBroker().post(IApplicationEvent.ITEM_SELECTED, null);
			}
		}
	};
	
	private final MouseListener tableMouseListener = new MouseAdapter() {
		@Override
		public void mouseDoubleClick(final MouseEvent e) {
			if (e.button != 1) {
				return;
			}
			performEdit();
		}
		
		@Override
		public void mouseDown(final MouseEvent e) {
			if (e.button != 3) {
				return;
			}
			final Menu menu = new Menu(tableViewer.getTable());
			tableViewer.getTable().setMenu(menu);
			buildDefaultContextualActions(menu);
			
			final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
			if (selection.isEmpty()) {
				return;
			}
			if (dataListModel.getActionModel().getDataListMenuActions().length > 0) {
				new MenuItem(menu, SWT.SEPARATOR);
			}
			
			buildContextualActions(menu, dataListModel.getActionModel().getDataListMenuActions(), tableViewer.getTable(), selection.getFirstElement());
		}
	};
	
	private final ModifyListener filterModifyListener = e -> {
		dataListFilter.setPattern(((Text) e.getSource()).getText());
		tableViewer.refresh();
	};
	
	public DataListComposite(final Composite parent, final int style, final DataListModel dataListModel) {
		super(parent, style);
		this.dataListModel = dataListModel;
		tableResizeController = new TableResizeController(dataListModel.getColumnDescriptors());
		buildUI();
	}
	
	public void buildUI() {
		
		setLayout(new GridLayout());
		
		if (dataListModel.isFilterDisplayed()) {
			final Composite filterComposite = new Composite(this, SWT.NONE);
			filterComposite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
			filterComposite.setLayout(new GridLayout(2, false));
			final CLabel filterLabel = new CLabel(filterComposite, SWT.NONE);
			filterLabel.setText("Rechercher: ");
			filterLabel.setImage(EImage.FIND.getSwtImage());
			final Text filterText = new Text(filterComposite, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
			filterText.setMessage("Rechercher un élément dans la liste");
			filterText.addModifyListener(filterModifyListener);
			filterText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		}
		
		final Composite actionBar = new Composite(this, SWT.NONE);
		actionBar.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));
		final GridLayout actionBarLayout = new GridLayout(2, false);
		actionBarLayout.marginWidth = 0;
		actionBar.setLayout(actionBarLayout);
		
		if (dataListModel.isReportButtonDisplayed()) {
			final ToolBar toolBar = new ToolBar(actionBar, SWT.RIGHT | SWT.VERTICAL);
			final ToolItem reportItem = new ToolItem(toolBar, SWT.DROP_DOWN);
			reportItem.setText("Rapports");
			reportItem.setImage(EImage.PRINT.getSwtImage());
			reportItem.addSelectionListener(new ReportListener());
		}
		
		final Composite buttonsComposite = new Composite(actionBar, SWT.NONE);
		buttonsComposite.setLayoutData(new GridData(GridData.END, GridData.BEGINNING, true, false));
		final GridLayout buttonsCompositeLayout = new GridLayout(4, false);
		buttonsCompositeLayout.marginWidth = 0;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		
		deleteButton = new Button(buttonsComposite, SWT.PUSH);
		deleteButton.setText("Supprimer");
		deleteButton.setEnabled(false);
		deleteButton.setImage(EImage.DELETE.getSwtImage());
		deleteButton.addSelectionListener(deleteActionController);
		deleteButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		
		editButton = new Button(buttonsComposite, SWT.PUSH);
		editButton.setText("Modifier");
		editButton.setEnabled(false);
		editButton.setImage(EImage.EDIT.getSwtImage());
		editButton.addSelectionListener(editActionController);
		editButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		
		addButton = new Button(buttonsComposite, SWT.PUSH);
		addButton.setText("Nouveau");
		addButton.setImage(EImage.CREATE.getSwtImage());
		addButton.addSelectionListener(createActionController);
		addButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		
		tableViewer = new TableViewer(this, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		dataListFilter = new DataListFilter(dataListModel.getXPathExpressions());
		tableViewer.setFilters(new ViewerFilter[] { dataListFilter });
		tableViewer.addSelectionChangedListener(tableSelectionListener);
		
		final Table table = tableViewer.getTable();
		table.addMouseListener(tableMouseListener);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.addListener(SWT.MeasureItem, event -> event.height = 25);
		
		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		
		for (final ColumnDescriptor columnDescriptor : dataListModel.getColumnDescriptors()) {
			final TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
			column.getColumn().setText(columnDescriptor.getColumnName());
		}
		
		table.addControlListener(tableResizeController);
		
		tableViewer.setContentProvider(new ObservableListContentProvider());
		tableViewer.getControl().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		
		tableViewer.setLabelProvider(dataListModel.getLabelProvider());
		tableViewer.setInput(dataListModel.getDataList());
	}
	
	public void select(final Object object) {
		tableViewer.setSelection(new StructuredSelection(object));
		tableViewer.refresh();
	}
	
	public Object getSelectedItem() {
		return selectedItem;
	}
	
	public DataListModel getDataListModel() {
		return dataListModel;
	}
	
	private void performEdit() {
		final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		final Object selectedObject = selection.getFirstElement();
		final DataListActionEvent event = new DataListActionEvent(tableViewer.getTable(), selectedObject, dataListModel);
		final IAction editAction = dataListModel.getActionModel().getEditAction();
		if (editAction != null) {
			final Object objEdited = editAction.execute(event);
			tableViewer.refresh();
			if (objEdited != null) {
				final int index = dataListModel.getDataList().indexOf(selectedObject);
				// dataListModel.getDataList().remove(index);
				dataListModel.getDataList().set(index, objEdited);
				tableViewer.setSelection(new StructuredSelection(objEdited));
				BundleUtil.getService(E4Service.class).getEventBroker().post(IApplicationEvent.ITEM_EDITED, objEdited);
			}
		}
	}
	
	private void buildDefaultContextualActions(final Menu menu) {
		if (tableViewer.getSelection().isEmpty()) {
			return;
		}
		Util.buildMenuItem(menu, "Modifier", EImage.EDIT.getSwtImage(), editActionController);
		Util.buildMenuItem(menu, "Supprimer", EImage.DELETE.getSwtImage(), deleteActionController);
		
		final Object selectedObject = ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
		if (selectedObject instanceof AbstractDocumentProvider) {
			
			new MenuItem(menu, SWT.SEPARATOR);
			
			final AbstractDocumentProvider documentProvider = (AbstractDocumentProvider) selectedObject;
			
			final List<ContextualAction> contextualActions = new ArrayList<>(documentProvider.getDocuments().size() + 1);
			
			final ContextualActionPathElement docElement = new ContextualActionPathElement("Documents", EImage.FOLDER.getSwtImage());
			final ContextualAction addAction = new ContextualAction(event -> {
				final List<Document> selectedDocuments = Util.selectDocuments();
				if (!selectedDocuments.isEmpty()) {
					documentProvider.getDocuments().addAll(selectedDocuments);
					BundleUtil.getService(IBasicDaoService.class).save(documentProvider);
					MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Ajout de document", "Document(s) ajouté(s) avec succès");
				}
				return null;
			}, new ContextualActionPathElement[] { docElement, new ContextualActionPathElement("Ajouter...", EImage.CREATE.getSwtImage()) });
			
			contextualActions.add(addAction);
			if (!documentProvider.getDocuments().isEmpty()) {
				contextualActions.addAll(documentProvider.getDocuments().stream().map(doc -> buildDocumentOpenAction(doc)).collect(Collectors.toList()));
			}
			
			buildContextualActions(menu, contextualActions.toArray(new ContextualAction[contextualActions.size()]), tableViewer.getTable(), selectedObject);
		}
	}
	
	private ContextualAction buildDocumentOpenAction(final Document document) {
		final ContextualActionPathElement docElement = new ContextualActionPathElement("Documents", EImage.FOLDER.getSwtImage());
		final ContextualActionPathElement openElement = new ContextualActionPathElement("Ouvrir", EImage.FILE.getSwtImage());
		final ContextualActionPathElement fileElement = new ContextualActionPathElement(document.getPath(), Util.getDefaultProgramImage(document));
		return new ContextualAction(event -> {
			Util.openDocument(document);
			return null;
		}, new ContextualActionPathElement[] { docElement, openElement, fileElement });
	}
	
	private void buildContextualActions(final Menu rootMenu, final ContextualAction[] contextualActions, final Object source, final Object target) {
		final Map<String, Menu> menus = new HashMap<>();
		for (final ContextualAction contextualAction : contextualActions) {
			final ContextualActionPathElement[] actionPath = contextualAction.getActionPath();
			if (actionPath.length == 1) {
				final SelectionAdapter itemSelectionController = new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						contextualAction.getAction().execute(new ActionEvent(source, target));
					}
				};
				Util.buildMenuItem(rootMenu, actionPath[0].getName(), actionPath[0].getImage(), itemSelectionController);
				continue;
			}
			for (int i = 0, iMax = actionPath.length; i < iMax; i++) {
				final Menu parentMenu = i > 0 ? menus.get(actionPath[i - 1].getName()) : rootMenu;
				if (i == iMax - 1) {
					final SelectionAdapter itemSelectionController = new SelectionAdapter() {
						@Override
						public void widgetSelected(final SelectionEvent e) {
							contextualAction.getAction().execute(new ActionEvent(source, target));
						}
					};
					Util.buildMenuItem(parentMenu, actionPath[i].getName(), actionPath[i].getImage(), itemSelectionController);
					
					continue;
				}
				Menu menu = menus.get(actionPath[i].getName());
				if (menu != null) {
					continue;
				} else {
					final MenuItem item = new MenuItem(parentMenu, SWT.CASCADE);
					item.setText(actionPath[i].getName());
					item.setImage(actionPath[i].getImage());
					menu = new Menu(parentMenu);
					item.setMenu(menu);
					menus.put(actionPath[i].getName(), menu);
				}
			}
		}
	}
}
