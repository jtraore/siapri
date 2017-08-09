package com.siapri.broker.app.views.common.customizer.databinding;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Widget;

public class DateTimeChooserObservableValue extends AbstractObservableValue implements ISWTObservable {

	private final DateTime dtWidget;

	private boolean updating = false;

	private Object oldValue;

	private final SelectionListener valueChangeListener = new SelectionAdapter() {

		@Override
		public void widgetSelected(final SelectionEvent e) {
			if (updating) {
				return;
			}
			final Date newValue = getDate(dtWidget);
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));

		}

	};

	public DateTimeChooserObservableValue(final DateTime dtWidget) {
		this(DisplayRealm.getRealm(dtWidget.getDisplay()), dtWidget);
	}

	public DateTimeChooserObservableValue(final Realm realm, final DateTime dtWidget) {
		super(realm);
		this.dtWidget = dtWidget;
		dtWidget.addSelectionListener(valueChangeListener);
		// dtWidget.addListener(eventType, listener);(modifyListener);
	}

	private Date getDate(DateTime dt) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.set(Calendar.YEAR, dt.getYear());
		calendar.set(Calendar.MONTH, dt.getMonth());
		calendar.set(Calendar.DAY_OF_MONTH, dt.getDay());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private void setDate(DateTime dt, Date date) {
		dt.setDate(date.getYear(), date.getMonth(), date.getDay());
	}

	@Override
	public Object getValueType() {
		return getDate(dtWidget);
	}

	@Override
	protected Object doGetValue() {
		return oldValue = getDate(dtWidget);
	}

	@Override
	protected void doSetValue(final Object value) {
		updating = true;
		setDate(dtWidget, ((Date) value));
		oldValue = value;
		updating = false;
	}

	@Override
	public synchronized void dispose() {
		dtWidget.removeSelectionListener(valueChangeListener);
		// dtWidget.removeModifyListener(modifyListener);
		super.dispose();
	}

	@Override
	public Widget getWidget() {
		return dtWidget;
	}
}
