package com.ngosdi.lawyer.app.views.common.customizer;

public abstract class AbstractCustomizerModel<T> {

	protected T target;

	public AbstractCustomizerModel(final T target) {
		this.target = target;
		if (target != null) {
			synchronize();
		}
	}

	protected abstract void synchronize();

	protected abstract void validate();

	public T getTarget() {
		return target;
	}
}
