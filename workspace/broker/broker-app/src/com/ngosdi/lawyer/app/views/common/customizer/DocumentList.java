package com.ngosdi.lawyer.app.views.common.customizer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;

import com.ngosdi.lawyer.app.views.common.EImage;
import com.ngosdi.lawyer.app.views.common.Util;
import com.siapri.broker.business.model.Document;

public class DocumentList {
	
	private final List<Document> documents;
	private final List<Document> documentsTemp;
	private final WritableList writableList;
	
	public DocumentList(final List<Document> documents) {
		this.documents = documents;
		documentsTemp = documents.stream().map(doc -> new Document(doc.getPath(), doc.getDescription())).collect(Collectors.toList());
		writableList = new WritableList(documentsTemp, Document.class);
	}
	
	public Composite createComposite(final Composite parent, final int style) {
		final Composite rootComposite = new Composite(parent, style);
		rootComposite.setLayout(new GridLayout());
		
		final Composite buttonsComposite = new Composite(rootComposite, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));
		buttonsComposite.setLayoutData(new GridData(GridData.END, GridData.CENTER, true, false));
		
		final Button removeButton = new Button(buttonsComposite, SWT.PUSH);
		removeButton.setText("Supprimer");
		removeButton.setEnabled(false);
		removeButton.setImage(EImage.DELETE.getSwtImage());
		removeButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		
		final Button addButton = new Button(buttonsComposite, SWT.PUSH);
		addButton.setText("Ajouter");
		addButton.setImage(EImage.CREATE.getSwtImage());
		addButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		
		final TableViewer documentViewer = new TableViewer(rootComposite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		final Table table = documentViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				if (e.button != 1) {
					return;
				}
				final ISelection selection = documentViewer.getSelection();
				if (!selection.isEmpty()) {
					Util.openDocument((Document) ((StructuredSelection) selection).getFirstElement());
				}
			}
			
			@Override
			public void mouseDown(final MouseEvent e) {
				if (e.button != 3) {
					return;
				}
				showMenu(documentViewer);
			}
		});
		
		final ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		documentViewer.setContentProvider(contentProvider);
		documentViewer.getControl().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		
		final TableViewerColumn columnPath = new TableViewerColumn(documentViewer, SWT.NONE);
		columnPath.getColumn().setText("Fichier");
		columnPath.getColumn().setWidth(300);
		
		final TableViewerColumn columnDescription = new TableViewerColumn(documentViewer, SWT.NONE);
		columnDescription.getColumn().setText("Description");
		columnDescription.getColumn().setWidth(300);
		columnDescription.setEditingSupport(new DescriptionEditingSupport(columnDescription.getViewer()));
		
		documentViewer.setLabelProvider(new DocumentListLabelProvider());
		documentViewer.setInput(writableList);
		
		documentViewer.addSelectionChangedListener(e -> removeButton.setEnabled(!e.getSelectionProvider().getSelection().isEmpty()));
		
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				addDocument(documentViewer);
			}
		});
		
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				removeDocument(documentViewer);
			}
		});
		
		enableDragAndDrop(documentViewer);
		
		return rootComposite;
	}
	
	public void validateUpdate() {
		documents.clear();
		documents.addAll(documentsTemp);
	}
	
	private void addDocument(final TableViewer documentViewer) {
		final List<Document> selectedDocuments = Util.selectDocuments();
		if (!selectedDocuments.isEmpty()) {
			writableList.addAll(selectedDocuments);
			documentViewer.setSelection(new StructuredSelection(writableList.get(writableList.size() - 1)));
		}
	}
	
	private void removeDocument(final TableViewer documentViewer) {
		final MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(), "Suppression", null, "Voulez-vous vraiment supprimer cet objet ?", MessageDialog.CONFIRM, new String[] { "Oui", "Non" }, 0);
		if (dialog.open() == Window.CANCEL) {
			return;
		}
		final Object selectedItem = ((IStructuredSelection) documentViewer.getSelection()).getFirstElement();
		writableList.remove(selectedItem);
	}
	
	private void showMenu(final TableViewer documentViewer) {
		final Menu menu = new Menu(documentViewer.getTable());
		documentViewer.getTable().setMenu(menu);
		
		final ISelection selection = documentViewer.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		
		final Document document = (Document) ((StructuredSelection) selection).getFirstElement();
		
		Util.buildMenuItem(menu, "Supprimer", EImage.DELETE.getSwtImage(), new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				removeDocument(documentViewer);
			}
		});
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		Util.buildMenuItem(menu, "Ouvrir le fichier avec le programme par defaut", Util.getDefaultProgramImage(document), new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				Util.openDocument(document);
			}
		});
		
		Util.buildMenuItem(menu, "Ouvrir l'emplacement du fichier", EImage.FOLDER.getSwtImage(), new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				try {
					Runtime.getRuntime().exec(String.format("explorer.exe /select,%s", document.getPath()));
				} catch (final IOException e1) {
					// TODO Log
					MessageDialog.openError(Display.getCurrent().getActiveShell(), "Ouverture de document", "Une erreur inconnue est survenu");
				}
			}
		});
	}
	
	private void enableDragAndDrop(final TableViewer documentViewer) {
		final DropTarget target = new DropTarget(documentViewer.getTable(), DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
		final FileTransfer fileTransfer = FileTransfer.getInstance();
		target.setTransfer(new Transfer[] { fileTransfer });
		target.addDropListener(new DropTargetAdapter() {
			@Override
			public void dragEnter(final DropTargetEvent event) {
				if (fileTransfer.isSupportedType(event.currentDataType)) {
					final String[] files = (String[]) fileTransfer.nativeToJava(event.currentDataType);
					if (Arrays.asList(files).stream().filter(path -> new File(path).isDirectory()).count() > 0) {
						event.detail = DND.DROP_NONE;
					}
				}
			}
			
			@Override
			public void drop(final DropTargetEvent event) {
				if (fileTransfer.isSupportedType(event.currentDataType)) {
					writableList.addAll(Arrays.asList((String[]) event.data).stream().filter(path -> new File(path).isFile()).map(path -> new Document(path, "")).collect(Collectors.toList()));
					documentViewer.setSelection(new StructuredSelection(writableList.get(writableList.size() - 1)));
				}
			}
		});
	}
	
	private final class DocumentListLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		@Override
		public Image getColumnImage(final Object object, final int column) {
			if (column == 0) {
				return Util.getDefaultProgramImage((Document) object);
			}
			return null;
		}
		
		@Override
		public String getColumnText(final Object object, final int column) {
			final Document document = (Document) object;
			switch (column) {
				case 0:
					return document.getPath();
				case 1:
					return document.getDescription();
			}
			return null;
		}
	}
	
	private class DescriptionEditingSupport extends EditingSupport {
		
		public DescriptionEditingSupport(final ColumnViewer viewer) {
			super(viewer);
		}
		
		@Override
		protected boolean canEdit(final Object arg0) {
			return true;
		}
		
		@Override
		protected CellEditor getCellEditor(final Object arg0) {
			return new TextCellEditor(((TableViewer) getViewer()).getTable());
		}
		
		@Override
		protected Object getValue(final Object obj) {
			final Document doc = (Document) obj;
			return doc.getDescription() != null ? doc.getDescription() : "";
		}
		
		@Override
		protected void setValue(final Object element, final Object value) {
			((Document) element).setDescription(String.valueOf(value));
			getViewer().update(element, new String[] { "description" });
			((TableViewer) getViewer()).getTable().layout(true);
		}
	}
	
}
