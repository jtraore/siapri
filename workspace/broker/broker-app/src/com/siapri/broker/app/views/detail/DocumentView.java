package com.siapri.broker.app.views.detail;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.business.model.AbstractEntity;
import com.siapri.broker.business.model.Document;
import com.siapri.broker.business.model.IDocumentProvider;

@Singleton
public class DocumentView {
	
	private Composite parent;
	private ScrolledComposite sc;
	
	@Inject
	public DocumentView() {
	}
	
	@PostConstruct
	public void postConstruct(final Composite parent) {
		this.parent = parent;
		parent.setLayout(new FillLayout());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
	}
	
	@Inject
	@Optional
	private void itemSelected(@UIEventTopic(IApplicationEvent.ITEM_SELECTED) final AbstractEntity item) {
		if (sc != null) {
			sc.dispose();
		}
		if (!(item instanceof IDocumentProvider)) {
			return;
		}
		
		displayDocumentsTable((IDocumentProvider) item);
	}
	
	private void displayDocumentsTable(final IDocumentProvider item) {
		sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		final Composite content = new Composite(sc, SWT.NONE);
		final FillLayout layout = new FillLayout();
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		content.setLayout(layout);
		sc.setContent(content);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);

		final TableViewer documentViewer = new TableViewer(content, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		final Table table = documentViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(new TableLayout());

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
		});

		documentViewer.setContentProvider(new ObservableListContentProvider());

		final TableViewerColumn columnAttribute = new TableViewerColumn(documentViewer, SWT.NONE);
		columnAttribute.getColumn().setText("Document");
		columnAttribute.getColumn().setWidth(500);

		final TableViewerColumn columnValue = new TableViewerColumn(documentViewer, SWT.NONE);
		columnValue.getColumn().setText("Description");
		columnValue.getColumn().setWidth(500);

		documentViewer.setLabelProvider(new DocumentTableLabelProvider());

		final WritableList<Document> writableList = new WritableList<>(item.getDocuments(), Document.class);
		documentViewer.setInput(writableList);
		
		parent.layout();
	}

	private final class DocumentTableLabelProvider extends LabelProvider implements ITableLabelProvider {

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
}