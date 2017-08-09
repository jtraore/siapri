package com.siapri.broker.app.views.taskbar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.PartViewService;

public class CurrentPartElementCountTask {

	@Inject
	private IStylingEngine stylingEngine;

	@Inject
	@Optional
	private PartViewService partViewService;

	@Inject
	private Composite parent;

	private Label label;

	@PostConstruct
	public void createGui() {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		composite.setLayout(gridLayout);
		label = new Label(composite, SWT.NONE);
		stylingEngine.setId(parent, "Taskbar");
		stylingEngine.setId(label, "TaskbarLabel");
	}

	@Inject
	@Optional
	private void itemsCountAvailable(@UIEventTopic(IApplicationEvent.ITEMS_COUNT) final int count) {
		if (count >= 0) {
			label.setText(String.format("   %d élément(s) affiché(s) dans la liste en cours", count));
		} else if (partViewService.getViewCount() == 0) {
			label.setText("");
		}
		parent.layout();
	}

}
