package com.ngosdi.lawyer.app.views.detail;

import org.eclipse.swt.widgets.Composite;

public interface IDetailCompositeProvider<T> {

	String getId();

	boolean canProvide(Object item);

	Composite createComposite(Composite parent, T item);
}
