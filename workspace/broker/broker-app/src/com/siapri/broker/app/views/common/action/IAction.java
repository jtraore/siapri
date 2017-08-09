package com.siapri.broker.app.views.common.action;

@FunctionalInterface
public interface IAction {

	public Object execute(ActionEvent event);
}
