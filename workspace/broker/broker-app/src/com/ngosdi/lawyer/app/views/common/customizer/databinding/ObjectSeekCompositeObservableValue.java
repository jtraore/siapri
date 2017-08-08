package com.ngosdi.lawyer.app.views.common.customizer.databinding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.swt.widgets.Widget;

import com.ngosdi.lawyer.app.views.common.customizer.ObjectSeekComposite;

public class ObjectSeekCompositeObservableValue extends AbstractObservableValue implements ISWTObservable {

	private final ObjectSeekComposite objectSeekComposite;

	private boolean updating = false;

	private Object oldValue;

	private final PropertyChangeListener valueChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(final PropertyChangeEvent event) {
			if (updating) {
				return;
			}
			final Object newValue = objectSeekComposite.getValue();
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));
		}
	};

	public ObjectSeekCompositeObservableValue(final ObjectSeekComposite objectSeekComposite) {
		this(DisplayRealm.getRealm(objectSeekComposite.getDisplay()), objectSeekComposite);
	}

	public ObjectSeekCompositeObservableValue(final Realm realm, final ObjectSeekComposite objectSeekComposite) {
		super(realm);
		this.objectSeekComposite = objectSeekComposite;
		objectSeekComposite.addValueChangeListener(valueChangeListener);
	}

	@Override
	public Object getValueType() {
		return objectSeekComposite.getElementType();
	}

	@Override
	protected Object doGetValue() {
		return oldValue = objectSeekComposite.getValue();
	}

	@Override
	protected void doSetValue(final Object value) {
		updating = true;
		objectSeekComposite.setValue(value);
		oldValue = value;
		updating = false;
	}

	@Override
	public synchronized void dispose() {
		objectSeekComposite.removeValueChangeListener(valueChangeListener);
		super.dispose();
	}

	@Override
	public Widget getWidget() {
		return objectSeekComposite;
	}
}
