package com.ngosdi.lawyer.app.views.common.customizer.databinding;

import java.util.Date;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Widget;

public class DateChooserObservableValue extends AbstractObservableValue implements ISWTObservable {

	private final DateChooserCombo cdtWidget;

	private boolean updating = false;

	private Object oldValue;

	private final ModifyListener modifyListener = new ModifyListener() {

		@Override
		public void modifyText(final ModifyEvent e) {
			if (updating) {
				return;
			}
			final Date newValue = cdtWidget.getValue();
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));
		}
	};

	private final SelectionListener valueChangeListener = new SelectionAdapter() {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			if (updating) {
				return;
			}
			final Date newValue = cdtWidget.getValue();
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));

		}

	};

	public DateChooserObservableValue(final DateChooserCombo cdtWidget) {
		this(DisplayRealm.getRealm(cdtWidget.getDisplay()), cdtWidget);
	}

	public DateChooserObservableValue(final Realm realm, final DateChooserCombo cdtWidget) {
		super(realm);
		this.cdtWidget = cdtWidget;
		cdtWidget.addSelectionListener(valueChangeListener);
		cdtWidget.addModifyListener(modifyListener);
	}

	@Override
	public Object getValueType() {
		return cdtWidget.getValue();
	}

	@Override
	protected Object doGetValue() {
		return oldValue = cdtWidget.getValue();
	}

	@Override
	protected void doSetValue(final Object value) {
		updating = true;
		cdtWidget.setValue((Date) value);
		oldValue = value;
		updating = false;
	}

	@Override
	public synchronized void dispose() {
		cdtWidget.removeSelectionListener(valueChangeListener);
		cdtWidget.removeModifyListener(modifyListener);
		super.dispose();
	}

	@Override
	public Widget getWidget() {
		return cdtWidget;
	}
}
