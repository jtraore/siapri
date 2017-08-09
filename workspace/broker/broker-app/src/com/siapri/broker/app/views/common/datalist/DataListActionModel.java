package com.siapri.broker.app.views.common.datalist;

import com.siapri.broker.app.views.common.action.ContextualAction;
import com.siapri.broker.app.views.common.action.IAction;

public class DataListActionModel {

	private final IAction createAction;
	private final IAction editAction;
	private final IAction deleteAction;
	private final ContextualAction[] dataListMenuActions;
	private final ContextualAction[] reportActions;

	public DataListActionModel(final IAction createAction, final IAction editAction, final IAction deleteAction) {
		this(createAction, editAction, deleteAction, null);
	}

	public DataListActionModel(final IAction createAction, final IAction editAction, final IAction deleteAction, final ContextualAction[] dataListMenuActions) {
		this(createAction, editAction, deleteAction, dataListMenuActions, null);
	}

	public DataListActionModel(final IAction createAction, final IAction editAction, final IAction deleteAction, final ContextualAction[] dataListMenuActions, final ContextualAction[] reportActions) {
		this.createAction = createAction;
		this.editAction = editAction;
		this.deleteAction = deleteAction;
		this.dataListMenuActions = dataListMenuActions;
		this.reportActions = reportActions;
	}

	public final IAction getCreateAction() {
		return createAction;
	}

	public final IAction getEditAction() {
		return editAction;
	}

	public final IAction getDeleteAction() {
		return deleteAction;
	}

	public final ContextualAction[] getDataListMenuActions() {
		if (dataListMenuActions == null) {
			return new ContextualAction[0];
		}
		return dataListMenuActions;
	}

	public ContextualAction[] getReportActions() {
		if (reportActions == null) {
			return new ContextualAction[0];
		}
		return reportActions;
	}
}
