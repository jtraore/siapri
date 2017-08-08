package com.ngosdi.lawyer.app.views.common.customizer.databinding;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.nebula.widgets.datechooser.DateChooserCombo;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.FormattedTextObservableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Widget;

import com.ngosdi.lawyer.app.views.common.customizer.ObjectSeekComposite;

public class BindingSupport {

	private final DataBindingContext bindingContext;
	private final AggregateValidationStatus aggregateValidationStatus;

	public BindingSupport() {
		bindingContext = new DataBindingContext();
		aggregateValidationStatus = new AggregateValidationStatus(bindingContext, AggregateValidationStatus.MAX_SEVERITY);
	}

	public void addChangeListener(final IChangeListener listener) {
		aggregateValidationStatus.addChangeListener(listener);
	}

	public void removeChangeListener(final IChangeListener listener) {
		aggregateValidationStatus.removeChangeListener(listener);
	}

	public IStatus getValidationStatus() {
		return (IStatus) aggregateValidationStatus.getValue();
	}

	public void bindText(final Object object, final String property, final Widget widget) {
		final IObservableValue widgetValue = WidgetProperties.text(SWT.Modify).observe(widget);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		bindingContext.bindValue(widgetValue, modelValue);
	}

	public void bindText(final Object object, final String property, final Widget widget, final IValidator validator) {
		final IObservableValue widgetValue = WidgetProperties.text(SWT.Modify).observe(widget);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		final UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
		updateValueStrategy.setBeforeSetValidator(validator);
		final Binding binding = bindingContext.bindValue(widgetValue, modelValue, updateValueStrategy, null);
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
	}

	public void bindComboViewer(final Object object, final String property, final ComboViewer comboViewer) {
		final IObservableValue viewerValue = ViewersObservables.observeSingleSelection(comboViewer);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		bindingContext.bindValue(viewerValue, modelValue);
	}

	public void bindComboViewer(final Object object, final String property, final ComboViewer comboViewer, final IValidator validator) {
		final IObservableValue viewerValue = ViewersObservables.observeSingleSelection(comboViewer);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		final UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
		updateValueStrategy.setBeforeSetValidator(validator);
		final Binding binding = bindingContext.bindValue(viewerValue, modelValue, updateValueStrategy, null);
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
	}

	public void bindFormattedText(final Object object, final String property, final FormattedText formattedText) {
		final FormattedTextObservableValue formattedTextValue = new FormattedTextObservableValue(formattedText, SWT.Modify);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		bindingContext.bindValue(formattedTextValue, modelValue);
	}

	public void bindFormattedText(final Object object, final String property, final FormattedText formattedText, final IValidator validator) {
		final FormattedTextObservableValue formattedTextValue = new FormattedTextObservableValue(formattedText, SWT.Modify);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		final UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
		updateValueStrategy.setBeforeSetValidator(validator);
		final Binding binding = bindingContext.bindValue(formattedTextValue, modelValue, updateValueStrategy, null);
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
	}

	public void bindObjectSeekComposite(final Object object, final String property, final ObjectSeekComposite objectSeekComposite) {
		final ObjectSeekCompositeObservableValue objectSeekCompositeObservableValue = new ObjectSeekCompositeObservableValue(objectSeekComposite);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		bindingContext.bindValue(objectSeekCompositeObservableValue, modelValue);
	}

	public void bindObjectSeekComposite(final Object object, final String property, final ObjectSeekComposite objectSeekComposite, final IValidator validator) {
		final ObjectSeekCompositeObservableValue objectSeekCompositeObservableValue = new ObjectSeekCompositeObservableValue(objectSeekComposite);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		final UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
		updateValueStrategy.setBeforeSetValidator(validator);
		final Binding binding = bindingContext.bindValue(objectSeekCompositeObservableValue, modelValue, updateValueStrategy, null);
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
	}

	public void bindCDateTimeWidget(final Object object, final String property, final CDateTime cdt, final IValidator validator) {
		final CDateTimeObservableValue cdtObservableValue = new CDateTimeObservableValue(cdt);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		final UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
		updateValueStrategy.setBeforeSetValidator(validator);
		final Binding binding = bindingContext.bindValue(cdtObservableValue, modelValue, updateValueStrategy, null);
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
	}

	public void bindDateChooserComboWidget(final Object object, final String property, final DateChooserCombo cdt, final IValidator validator) {
		final DateChooserObservableValue cdtObservableValue = new DateChooserObservableValue(cdt);
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		final UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
		updateValueStrategy.setBeforeSetValidator(validator);
		final Binding binding = bindingContext.bindValue(cdtObservableValue, modelValue, updateValueStrategy, null);
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
	}

	public void bindDateTimeChooserComboWidget(final Object object, final String property, final DateTime dt, final IValidator validator) {
		final IObservableValue modelValue = BeanProperties.value(property).observe(object);
		ISWTObservableValue dtObservableValue = WidgetProperties.selection().observe(dt);
		final UpdateValueStrategy updateValueStrategy = new UpdateValueStrategy();
		updateValueStrategy.setBeforeSetValidator(validator);
		final Binding binding = bindingContext.bindValue(dtObservableValue, modelValue, updateValueStrategy, null);
		ControlDecorationSupport.create(binding, SWT.LEFT | SWT.TOP);
	}
}
