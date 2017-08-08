package com.ngosdi.lawyer.app.views.common.action;

@FunctionalInterface
public interface IAction {

	public Object execute(ActionEvent event);
}
