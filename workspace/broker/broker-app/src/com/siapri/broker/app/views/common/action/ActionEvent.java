package com.siapri.broker.app.views.common.action;

public class ActionEvent {

	private final Object source;
	private final Object target;

	public ActionEvent(final Object source) {
		this(source, null);
	}

	public ActionEvent(final Object source, final Object target) {
		this.source = source;
		this.target = target;
	}

	public final Object getSource() {
		return source;
	}

	public final Object getTarget() {
		return target;
	}
}
