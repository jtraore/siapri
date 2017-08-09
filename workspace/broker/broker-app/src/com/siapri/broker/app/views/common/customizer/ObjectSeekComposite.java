package com.siapri.broker.app.views.common.customizer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class ObjectSeekComposite extends Composite {

	public static final String VALUE_PROPERTY = "value";

	private final PropertyChangeSupport propertyChangeSupport;

	private final SearchContext searchContext;
	private Object value;
	private Text searchText;
	private Button searchButton;

	private Shell popupShell;
	private TableViewer tableViewer;

	private boolean textModifyListenerEnabled;

	public ObjectSeekComposite(final Composite parent, final SearchContext searchContext) {
		super(parent, SWT.NONE);
		this.searchContext = searchContext;
		textModifyListenerEnabled = true;
		propertyChangeSupport = new PropertyChangeSupport(this);
		buildUI();
	}

	private void buildUI() {
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);
		searchText = new Text(this, SWT.BORDER);
		searchText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		searchButton = new Button(this, SWT.PUSH);
		final GridData gridData = new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false);
		gridData.heightHint = 22;
		searchButton.setLayoutData(gridData);
		searchButton.setText("...");
		setupSearchListener();
		setupContentAssist();
	}

	private void setupContentAssist() {
		popupShell = new Shell(getShell(), SWT.ON_TOP);
		popupShell.setLayout(new FillLayout());

		final LabelProvider labelProvider = searchContext.getLabelProvider();
		tableViewer = new TableViewer(popupShell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ObservableListContentProvider());
		tableViewer.getControl().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setContentProvider(new ObservableListContentProvider());
		tableViewer.setInput(searchContext.getDataListModel().getDataList());
		final Table table = tableViewer.getTable();

		final ViewerFilter filter = new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
				final String pattern = ".*" + searchText.getText().toLowerCase() + ".*";
				return labelProvider.getText(element).toLowerCase().matches(pattern);
			}
		};
		tableViewer.setFilters(new ViewerFilter[] { filter });

		searchText.addListener(SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				switch (event.keyCode) {
					case SWT.ARROW_DOWN:
						int index = (table.getSelectionIndex() + 1) % table.getItemCount();
						table.setSelection(index);
						event.doit = false;
						break;
					case SWT.ARROW_UP:
						index = table.getSelectionIndex() - 1;
						if (index < 0) {
							index = table.getItemCount() - 1;
						}
						table.setSelection(index);
						event.doit = false;
						break;
					case SWT.CR:
						if (!popupShell.isVisible()) {
							break;
						}
						updateValue();
						popupShell.setVisible(false);
						break;
					case SWT.ESC:
						popupShell.setVisible(false);
						break;
				}
			}
		});

		searchText.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (!textModifyListenerEnabled) {
					return;
				}
				tableViewer.refresh();
				if (searchText.getText().length() == 0) {
					popupShell.setVisible(false);
					textModifyListenerEnabled = false;
					setValue(null);
					textModifyListenerEnabled = true;
				} else {
					final Rectangle textBounds = Display.getCurrent().map(searchText, null, searchText.getBounds());
					popupShell.setBounds(textBounds.x - 2, textBounds.y - 7 + textBounds.height, textBounds.width, 150);
					popupShell.setVisible(true);
				}
			}
		});

		table.addListener(SWT.DefaultSelection, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				updateValue();
				popupShell.setVisible(false);
			}
		});

		table.addListener(SWT.KeyDown, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (event.keyCode == SWT.ESC) {
					popupShell.setVisible(false);
				}
			}
		});

		final Listener focusOutListener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				Display.getCurrent().asyncExec(() -> {
					if (Display.getCurrent().isDisposed()) {
						return;
					}
					final Control control = Display.getCurrent().getFocusControl();
					if (control == null || control != searchText && control != table) {
						popupShell.setVisible(false);
					}
				});
			}
		};
		table.addListener(SWT.FocusOut, focusOutListener);
		searchText.addListener(SWT.FocusOut, focusOutListener);

		getShell().addListener(SWT.Move, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				popupShell.setVisible(false);
			}
		});
	}

	private void updateValue() {
		final IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		setValue(selection.getFirstElement());
	}

	private void setupSearchListener() {
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final FindDialog dialog = new FindDialog(getShell(), searchContext.getDataListModel(), searchContext.getTitle(), searchContext.getDescription());
				if (dialog.open() != Dialog.OK) {
					return;
				}
				textModifyListenerEnabled = false;
				setValue(dialog.getSelectedItem());
				textModifyListenerEnabled = true;
			}
		});
	}

	public final Object getValue() {
		return value;
	}

	public final void setValue(final Object value) {
		final Object oldValue = this.value;
		this.value = value;
		if (value != null) {
			searchText.setText(searchContext.getLabelProvider().getText(value));
		} else {
			searchText.setText("");
		}
		propertyChangeSupport.firePropertyChange(VALUE_PROPERTY, oldValue, value);
	}

	public final void addValueChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(VALUE_PROPERTY, listener);
	}

	public final void removeValueChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(VALUE_PROPERTY, listener);
	}

	public Object getElementType() {
		return searchContext.getDataListModel().getElementType();
	}
}
