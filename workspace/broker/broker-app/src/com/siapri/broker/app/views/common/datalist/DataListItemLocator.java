package com.siapri.broker.app.views.common.datalist;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

import com.siapri.broker.app.Activator;
import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.E4Service;
import com.siapri.broker.app.PartViewService;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.overview.IOverviewItemLocator;
import com.siapri.broker.app.views.overview.OverviewItem;

public abstract class DataListItemLocator<T> implements IOverviewItemLocator<T> {

	protected abstract Class<? extends PartView<T>> getPartViewClass();

	protected abstract String getToolItemId();

	@Override
	public void locate(final OverviewItem<T> item) {
		final String partId = showView();
		BundleUtil.getService(PartViewService.class).getPartView(partId).select(item.getTarget());
	}

	private String showView() {
		final E4Service e4Service = BundleUtil.getService(E4Service.class);
		final MPartStack partStack = (MPartStack) e4Service.getModelService().find(Activator.MAIN_PART_STACK_ID, e4Service.getApplication());
		final MDirectToolItem directToolItem = (MDirectToolItem) e4Service.getModelService().find(getToolItemId(), e4Service.getApplication());
		final String partId = partStack.getElementId() + "." + getToolItemId();

		if (!directToolItem.isSelected()) {
			final MPart newPart = e4Service.getPartService().createPart(Activator.MAIN_PART_DESCRIPTOR_ID);
			newPart.setElementId(partId);
			newPart.setContributionURI(getContributionUri());
			newPart.setIconURI(directToolItem.getIconURI().replace(".png", "_small.png"));
			newPart.setLabel(directToolItem.getLabel());
			partStack.getChildren().add(newPart);
			e4Service.getPartService().showPart(newPart, PartState.ACTIVATE);
			directToolItem.setSelected(true);
		} else {
			e4Service.getPartService().activate(e4Service.getPartService().findPart(partId));
		}
		return partId;
	}

	private String getContributionUri() {
		return Activator.BUNDLE_URI_PREFIX + getPartViewClass().getName();
	}
}
