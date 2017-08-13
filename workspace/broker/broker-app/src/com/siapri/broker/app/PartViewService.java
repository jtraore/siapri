package com.siapri.broker.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

import com.siapri.broker.app.views.NavigationManager;
import com.siapri.broker.app.views.PartView;
import com.siapri.broker.app.views.detail.IDetailCompositeProvider;

public class PartViewService {

	private final Map<String, String> partViewMapping = new HashMap<>();

	private final Map<String, PartView<?>> partViews = new HashMap<>();
	private final Map<String, IDetailCompositeProvider<?>> detailCompositeProviders = new HashMap<>();
	private final NavigationManager navigationManager = new NavigationManager();

	public PartViewService() {
		// partViewMapping.put("broker-app.toolbar.main.item.clients", Activator.BUNDLE_URI_PREFIX + ClientView.class.getName());
		// partViewMapping.put("broker-app.toolbar.main.item.adversary", Activator.BUNDLE_URI_PREFIX + AdversaryView.class.getName());
		// partViewMapping.put("broker-app.toolbar.main.item.cases", Activator.BUNDLE_URI_PREFIX + CaseView.class.getName());
		// partViewMapping.put("broker-app.toolbar.main.item.courts", Activator.BUNDLE_URI_PREFIX + CourtView.class.getName());
		// partViewMapping.put("broker-app.toolbar.main.item.hearings", Activator.BUNDLE_URI_PREFIX + HearingView.class.getName());
		// partViewMapping.put("broker-app.toolbar.main.item.payments", Activator.BUNDLE_URI_PREFIX + PaymentView.class.getName());
	}

	public Map<String, String> getPartViewMapping() {
		return partViewMapping;
	}

	public void addPartView(final String id, final PartView<?> partView) {
		partViews.put(id, partView);
	}

	public PartView<?> getPartView(final String id) {
		return partViews.get(id);
	}

	public void removePartView(final String id) {
		partViews.remove(id);
	}

	public int getViewCount() {
		return partViews.size();
	}

	public final void addDetailCompositeProvider(final IDetailCompositeProvider<?> provider) {
		detailCompositeProviders.put(provider.getId(), provider);
	}

	public final Optional<IDetailCompositeProvider<?>> getDetailCompositeProvider(final Object item) {
		return detailCompositeProviders.values().stream().filter(provider -> provider.canProvide(item)).findFirst();
	}

	public final IDetailCompositeProvider<?> getDetailCompositeProvider(final String id) {
		return detailCompositeProviders.get(id);
	}

	public final void removeDetailCompositeProvider(final String id) {
		detailCompositeProviders.remove(id);
	}

	public NavigationManager getNavigationManager() {
		return navigationManager;
	}

	public String showView(final String partId) {
		final E4Service e4Service = BundleUtil.getService(E4Service.class);
		final MPartStack partStack = (MPartStack) e4Service.getModelService().find(Activator.MAIN_PART_STACK_ID, e4Service.getApplication());
		final String viewId = partId.substring((Activator.MAIN_PART_STACK_ID + ".").length());
		final MDirectToolItem directToolItem = (MDirectToolItem) e4Service.getModelService().find(viewId, e4Service.getApplication());

		if (!directToolItem.isSelected()) {
			final MPart newPart = e4Service.getPartService().createPart(Activator.MAIN_PART_DESCRIPTOR_ID);
			newPart.setElementId(partId);
			newPart.setContributionURI(getPartViewMapping().get(viewId));
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

}
