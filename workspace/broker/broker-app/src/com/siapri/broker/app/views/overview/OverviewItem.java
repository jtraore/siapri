package com.siapri.broker.app.views.overview;

public class OverviewItem<T> {

	private T target;
	private String text;

	public OverviewItem(final T target, final String text) {
		this.target = target;
		this.text = text;
	}

	public final T getTarget() {
		return target;
	}

	public final void setTarget(final T target) {
		this.target = target;
	}

	public final String getText() {
		return text;
	}

	public final void setText(final String text) {
		this.text = text;
	}
}
