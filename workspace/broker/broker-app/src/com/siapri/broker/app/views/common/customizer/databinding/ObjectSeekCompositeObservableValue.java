package com.siapri.broker.app.views.common.customizer.databinding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.swt.widgets.Widget;

import com.siapri.broker.app.views.common.customizer.ObjectSeekComposite;

@SuppressWarnings("rawtypes")
public class ObjectSeekCompositeObservableValue extends AbstractObservableValue implements ISWTObservable {
	
	private final ObjectSeekComposite targetComposite;
	
	private boolean updating = false;
	
	private Object oldValue;
	
	private final PropertyChangeListener valueChangeListener = new PropertyChangeListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void propertyChange(final PropertyChangeEvent event) {
			if (updating) {
				return;
			}
			final Object newValue = targetComposite.getValue();
			fireValueChange(Diffs.createValueDiff(oldValue, newValue));
		}
	};
	
	public ObjectSeekCompositeObservableValue(final ObjectSeekComposite objectSeekComposite) {
		this(DisplayRealm.getRealm(objectSeekComposite.getDisplay()), objectSeekComposite);
	}
	
	public ObjectSeekCompositeObservableValue(final Realm realm, final ObjectSeekComposite objectSeekComposite) {
		super(realm);
		targetComposite = objectSeekComposite;
		objectSeekComposite.addValueChangeListener(valueChangeListener);
	}
	
	@Override
	public Object getValueType() {
		return targetComposite.getElementType();
	}
	
	@Override
	protected Object doGetValue() {
		return oldValue = targetComposite.getValue();
	}
	
	@Override
	protected void doSetValue(final Object value) {
		updating = true;
		targetComposite.setValue(value);
		oldValue = value;
		updating = false;
	}
	
	@Override
	public synchronized void dispose() {
		targetComposite.removeValueChangeListener(valueChangeListener);
		super.dispose();
	}
	
	@Override
	public Widget getWidget() {
		return targetComposite;
	}
}
