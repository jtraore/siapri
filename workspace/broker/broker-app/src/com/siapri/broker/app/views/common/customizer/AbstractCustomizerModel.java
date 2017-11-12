package com.siapri.broker.app.views.common.customizer;

public abstract class AbstractCustomizerModel<T> {
	
	protected T target;

	public AbstractCustomizerModel(final T target) {
		this(target, true);
	}
	
	public AbstractCustomizerModel(final T target, final boolean synchronize) {
		this.target = target;
		if (synchronize && target != null) {
			synchronize();
		}
	}
	
	protected abstract void synchronize();
	
	protected abstract void validate();
	
	public T getTarget() {
		return target;
	}
}
