
package com.siapri.broker.app.views.detail;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.app.views.conversation.ConversationDataListModel;
import com.siapri.broker.business.model.AbstractEntity;
import com.siapri.broker.business.model.Client;

public class ConversationView {
	
	@Inject
	private MPart part;

	private Composite parent;
	private ScrolledComposite sc;

	@Inject
	public ConversationView() {
	}

	@PostConstruct
	public void postConstruct(final Composite parent) {
		this.parent = parent;
		parent.setLayout(new FillLayout());
		parent.setBackgroundMode(SWT.INHERIT_FORCE);
		
		final Object item = part.getTransientData().get("item");
		if (item instanceof Client) {
			displayTable((Client) item);
		}
	}

	@Inject
	@Optional
	private void itemSelected(@UIEventTopic(IApplicationEvent.ITEM_SELECTED) final AbstractEntity item) {
		if (sc != null) {
			sc.dispose();
		}
		if (!(item instanceof Client)) {
			return;
		}
		displayTable((Client) item);
	}
	
	private void displayTable(final Client item) {
		sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		final Composite content = new Composite(sc, SWT.NONE);
		final FillLayout layout = new FillLayout();
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		content.setLayout(layout);
		sc.setContent(content);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		
		final DataListModel dataListModel = new ConversationDataListModel(content, item);
		dataListModel.setFilterDisplayed(false);
		dataListModel.setReportButtonDisplayed(false);
		dataListModel.setSelectionEventActivated(false);
		final DataListComposite dataListComposite = new DataListComposite(content, SWT.NONE, dataListModel);
		dataListComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		parent.layout();
	}
	
}