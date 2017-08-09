package com.siapri.broker.app.views.common.customizer.databinding;

import java.util.Date;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Widget;

public class CDateTimeObservableValue extends AbstractObservableValue implements ISWTObservable {

	private final CDateTime cdtWidget;

	private boolean updating = false;

	private Object oldValue;

	private final SelectionListener valueChangeListener = new SelectionAdapter() {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			if (updating) {
				return;
			}
			final Date newValue = cdtWidget.getSelection();
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));

		}

	};

	public CDateTimeObservableValue(final CDateTime cdtWidget) {
		this(DisplayRealm.getRealm(cdtWidget.getDisplay()), cdtWidget);
	}

	public CDateTimeObservableValue(final Realm realm, final CDateTime cdtWidget) {
		super(realm);
		this.cdtWidget = cdtWidget;
		cdtWidget.addSelectionListener(valueChangeListener);
	}

	@Override
	public Object getValueType() {
		return cdtWidget.getSelection();
	}

	@Override
	protected Object doGetValue() {
		return oldValue = cdtWidget.getSelection();
	}

	@Override
	protected void doSetValue(final Object value) {
		updating = true;
		cdtWidget.setSelection((Date) value);
		oldValue = value;
		updating = false;
	}

	@Override
	public synchronized void dispose() {
		cdtWidget.removeSelectionListener(valueChangeListener);
		super.dispose();
	}

	@Override
	public Widget getWidget() {
		return cdtWidget;
	}
}
