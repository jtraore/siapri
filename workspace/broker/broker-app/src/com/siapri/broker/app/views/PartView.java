package com.siapri.broker.app.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.Activator;
import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.IApplicationEvent;
import com.siapri.broker.app.PartViewService;
import com.siapri.broker.app.views.common.datalist.DataListComposite;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.AbstractEntity;

public abstract class PartView<T> {
	
	@Inject
	protected MPart currentPart;
	
	@Inject
	protected MApplication application;
	
	@Inject
	protected EModelService modelService;
	
	@Inject
	protected PartViewService partViewService;
	
	@Inject
	private IEventBroker eventBroker;
	
	@Inject
	@Preference
	private IEclipsePreferences preferences;
	
	protected DataListComposite dataListComposite;
	
	protected DataListModel dataListModel;
	
	protected abstract void createGui(final Composite parent);
	
	@Inject
	public PartView() {
	}
	
	@PostConstruct
	public void postConstruct(final Composite parent) {
		createGui(parent);
		dataListComposite = new DataListComposite(parent, SWT.NONE, dataListModel);
		dataListComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		BundleUtil.getService(PartViewService.class).addPartView(currentPart.getElementId(), this);
	}
	
	@PreDestroy
	public void preDestroy() {
		final String toolItemId = currentPart.getElementId().substring((Activator.MAIN_PART_STACK_ID + ".").length());
		final MDirectToolItem directToolItem = (MDirectToolItem) modelService.find(toolItemId, application);
		directToolItem.setSelected(false);
		BundleUtil.getService(PartViewService.class).removePartView(currentPart.getElementId());
		BundleUtil.getService(PartViewService.class).removeDetailCompositeProvider(currentPart.getElementId());
		eventBroker.post(IApplicationEvent.ITEMS_COUNT, -1);
	}
	
	public void select(final Object object) {
		dataListComposite.select(object);
	}
	
	public DataListComposite getDataListComposite() {
		return dataListComposite;
	}
	
	@Inject
	@Optional
	private void itemCreated(@UIEventTopic(IApplicationEvent.ITEM_CREATED) final Object item) {
		if (item.getClass().equals(dataListModel.getElementType()) && !dataListModel.getDataList().contains(item)) {
			dataListModel.getDataList().add(item);
		}
	}
	
	@Inject
	@Optional
	private void itemSelected(@UIEventTopic(IApplicationEvent.ITEM_SELECTED) final AbstractEntity item) {
		if (item.getClass().equals(dataListModel.getElementType()) && dataListModel.getDataList().contains(item)) {
			preferences.putLong(currentPart.getElementId() + ".item.selection", item.getId());
		}
	}
	
}
