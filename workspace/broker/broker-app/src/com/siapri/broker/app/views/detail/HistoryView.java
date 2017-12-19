package com.siapri.broker.app.views.detail;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.business.model.AbstractEntity;

@Singleton
public class HistoryView {

	private Composite parent;
	private ScrolledComposite sc;

	@Inject
	public HistoryView() {
	}

	@PostConstruct
	public void postConstruct(final Composite parent) {
		this.parent = parent;
		parent.setLayout(new FillLayout());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
	}

	@Inject
	@Optional
	private void itemSelected(@UIEventTopic(IApplicationEvent.ITEM_SELECTED) final AbstractEntity item) {
		if (sc != null) {
			sc.dispose();
		}
		if (item == null) {
			return;
		}

		displayHistory(item);
	}

	private void displayHistory(final AbstractEntity item) {
		sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		final Composite content = new Composite(sc, SWT.NONE);
		final FillLayout layout = new FillLayout();
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		content.setLayout(layout);
		sc.setContent(content);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);

		// @formatter:off
		final String history = String.format("Date de création : %s\nDate de dernière modification : %s\nCréateur : %s\nModificateur : %s",
				Util.DATE_TIME_FORMATTER.format(item.getCreatedDate()),
				item.getLastModifiedDate() != null ? Util.DATE_TIME_FORMATTER.format(item.getLastModifiedDate()) : "",
				item.getCreatedBy() != null ? item.getCreatedBy().getLogin() : "",
				item.getLastModifiedBy() != null ? item.getLastModifiedBy().getLogin() : "");
		// @formatter:on

		final StyledText control = new StyledText(content, SWT.WRAP | SWT.MULTI);
		control.setText(history);
		control.setEditable(false);
		control.setStyleRange(Util.createStyleRange(control.getText(), "Date de création", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Date de dernière modification", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Créateur", SWT.BOLD));
		control.setStyleRange(Util.createStyleRange(control.getText(), "Modificateur", SWT.BOLD));

		parent.layout();
	}
}