package com.siapri.broker.app.views.common.action;

public class ContextualAction {

	private final IAction action;
	private final ContextualActionPathElement[] actionPath;

	public ContextualAction(final IAction action, final ContextualActionPathElement[] actionPath) {
		this.action = action;
		this.actionPath = actionPath;
	}

	public ContextualActionPathElement[] getActionPath() {
		return actionPath;
	}

	public IAction getAction() {
		return action;
	}
}
